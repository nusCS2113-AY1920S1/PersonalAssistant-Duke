package duke.command;

import duke.exception.DukeException;
import duke.exception.DukeHelpException;

public class ArgParser {

    enum ParseState {
        EMPTY, //not parsing anything currently
        ARG, //parsing a single-word argument for a switch or the command itself
        STRING, //parsing a quoted string
        SWITCH //parsing a switch name
    }

    private ArgCommand currCommand;
    private StringBuilder elementBuilder;
    private ParseState state;
    private String currSwitchName;
    private boolean isEscaped;

    /**
     * Parses the user's input after the Command name and loads the parameters for the Command from it.
     *
     * @param inputStr The input provided by the user for this command, without the command keyword and stripped.
     * @throws DukeException If input was in the wrong format, contained invalid values, or was otherwise unable to be
     *                       parsed.
     */
    public void parseArgument(ArgCommand command, String inputStr) throws DukeException {
        currCommand = command;
        assert (!inputStr.contains("\r"));
        if (inputStr.length() == 0) {
            checkEmptyString();
        }

        state = ParseState.EMPTY;
        currSwitchName = null;
        elementBuilder = new StringBuilder();
        isEscaped = false;

        // TODO: identify situations with command arguments after no-arg switches

        //FSM :D
        for (int i = 0; i < inputStr.length(); ++i) {
            char curr = inputStr.charAt(i);
            switch (state) {
            case EMPTY:
                handleEmpty(curr);
                break;
            case ARG:
                handleArg(curr);
                break;
            case STRING:
                handleString(curr);
                break;
            case SWITCH:
                handleSwitch(curr);
                break;
            default:
                throw new DukeException("Invalid parser state!");
            }
        }

        //cleanup and check if states exited correctly
        switch (state) {
        case EMPTY:
            break;
        case STRING: //fallthrough; assume the user forgot to close the string
        case ARG:
            writeElement();
            break;
        case SWITCH:
            addSwitch();
            break;
        default:
            throw new DukeException("Invalid parser state!");
        }

        if (currSwitchName != null) {
            currCommand.initSwitchVal(currSwitchName, null);
        }
        currCommand.checkCommandValid();
    }

    private void handleEmpty(char curr) throws DukeHelpException {
        switch (curr) {
        case '-':
            state = ParseState.SWITCH;
            break;
        case '"':
            state = ParseState.STRING;
            break;
        case '\n': //fallthrough
        case ' ': //skip spaces
            break;
        default:
            elementBuilder.append(curr);
            state = ParseState.ARG;
            break;
        }
    }

    private void handleArg(char curr) throws DukeException {
        switch (curr) {
        case '"':
            if (!isEscaped) {
                throw new DukeException("Unescaped double quotes in argument: " + elementBuilder.toString());
            } //fallthrough
        case '-':
            if (!isEscaped) {
                throw new DukeException("Unescaped hyphen in argument: " + elementBuilder.toString());
            } //fallthrough
        case '\\':
            if (!isEscaped) {
                isEscaped = true;
                break;
            } //fallthrough
        case '\n': //fallthrough
        case ' ':
            if (!isEscaped) {
                writeElement();
                break;
            } //fallthrough
        default:
            isEscaped = false;
            elementBuilder.append(curr);
            break;
        }
    }

    private void handleString(char curr) throws DukeHelpException {
        switch (curr) {
        case '"':
            if (!isEscaped) {
                writeElement();
                break;
            } //fallthrough
        case '\\':
            if (!isEscaped) {
                isEscaped = true;
                break;
            } //fallthrough
        default:
            isEscaped = false;
            elementBuilder.append(curr);
            break;
        }
    }

    private void handleSwitch(char curr) throws DukeHelpException {
        switch (curr) {
        case '"':
            state = ParseState.STRING;
            addSwitch();
            break;
        case '\n': //fallthrough
        case ' ':
            state = ParseState.EMPTY;
            addSwitch();
            break;
        case '-':
            addSwitch();
            break;
        default:
            elementBuilder.append(curr);
            break;
        }
    }

    private void writeElement() throws DukeHelpException {
        if (currSwitchName == null && currCommand.getArg() != null) {
            throw new DukeHelpException("I don't know what you're trying to tell me with the word '"
            + elementBuilder.toString() + "'!", currCommand);
        };
        // if ambiguous whether argument is for command or switch, favour switch
        if (currSwitchName != null) {
            if (currCommand.isArgForbidden(currSwitchName)) { // switch should not have arguments
                currCommand.initSwitchVal(currSwitchName, null);
                currCommand.initArg(elementBuilder.toString());
            } else {
                currCommand.initSwitchVal(currSwitchName, elementBuilder.toString());
            }
            currSwitchName = null;
        } else { //currCommand.arg == null
            currCommand.initArg(elementBuilder.toString());
        }
        elementBuilder.setLength(0); //clear elementBuilder
        state = ParseState.EMPTY;
    }

    private void addSwitch() throws DukeHelpException {
        String newSwitchName = elementBuilder.toString().toLowerCase();

        // previous switch was not given an argument
        if (currSwitchName != null) {
            currCommand.initSwitchVal(currSwitchName, null);
        }

        // search for switch name in switch name map, then use algorithm to find it if necessary
        if (currCommand.hasSwitch(newSwitchName)) {
            String findSwitchName = CommandUtils.findSwitch(newSwitchName, currCommand);
            if (findSwitchName == null) {
                throw new DukeHelpException("I don't know what this switch is: " + newSwitchName, currCommand);
            }
            newSwitchName = findSwitchName;
        }

        currSwitchName = newSwitchName;
        elementBuilder.setLength(0); //clear elementBuilder
    }

    // TODO refactor into argcommand
    private void checkEmptyString() throws DukeException {
        boolean canBeEmpty = true;
        if (currCommand.getCmdArgLevel() == ArgLevel.REQUIRED) {
            canBeEmpty = false;
        } else {
            for (Switch switchData : currCommand.getSwitchMap().values()) {
                if (!switchData.isOptional) {
                    canBeEmpty = false;
                    break;
                }
            }
        }
        if (!canBeEmpty) {
            throw new DukeException(currCommand.getEmptyArgMsg());
        }
    }
}
