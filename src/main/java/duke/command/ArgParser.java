package duke.command;

import duke.exception.DukeException;
import duke.exception.DukeHelpException;

import java.util.HashMap;
import java.util.Map;

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
    private Map<String, Switch> switchMap;
    private HashMap<String, String> switchVals;

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
        switchMap = currCommand.getSwitchMap();
        switchVals = new HashMap<String, String>();
        elementBuilder = new StringBuilder();
        isEscaped = false;

        //FSM :D
        for (int i = 0; i < inputStr.length(); ++i) {
            char curr = inputStr.charAt(i);
            switch (state) {
            case ARG:
                handleArg(curr);
                break;
            case EMPTY:
                handleEmpty(curr);
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
        case ARG:
            writeElement();
            break;
        case SWITCH:
            addSwitch();
            break;
        default:
            throw new DukeException("Invalid parser state!");
        }

        checkCommandValid();
        currCommand.setSwitchValsMap(switchVals);
    }

    private void handleArg(char curr) throws DukeException {
        switch (curr) {
        case '\\':
            if (!isEscaped) {
                isEscaped = true;
                break;
            } //fallthrough
        case '-':
            if (!isEscaped) {
                checkSwitchAllowed();
                writeElement();
                state = ParseState.SWITCH;
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
        case ' ':
            state = (switchMap.get(currSwitchName).argLevel == ArgLevel.NONE) ? ParseState.EMPTY : ParseState.ARG;
            addSwitch();
            break;
        case '-':
            checkSwitchAllowed();
            addSwitch();
            break;
        default:
            elementBuilder.append(curr);
            break;
        }
    }

    private void handleEmpty(char curr) throws DukeHelpException {
        switch (curr) {
        case ' ': //ignore blank characters
        case '\n':
            break;
        case '-':
            checkSwitchAllowed();
            state = ParseState.SWITCH;
            break;
        default:
            checkArgAllowed();
            state = ParseState.ARG;
            break;
        }
    }

    private void writeElement() {
        assert (currSwitchName != null || currCommand.getArg() == null);
        // if ambiguous whether argument is for command or switch, favour switch
        if (currSwitchName != null) {
            switchVals.put(currSwitchName, elementBuilder.toString());
        } else { //currCommand.arg == null
            currCommand.setArg(elementBuilder.toString());
        }
        elementBuilder.setLength(0); //clear elementBuilder
    }

    private void addSwitch() throws DukeHelpException {
        currSwitchName = elementBuilder.toString();

        if (!switchMap.containsKey(currSwitchName)) {
            String findSwitchName = CommandUtils.findSwitch(currSwitchName, currCommand);
            if (findSwitchName == null) {
                throw new DukeHelpException("I don't know what this switch is '" + currSwitchName + "'!", currCommand);
            }
            currSwitchName = findSwitchName;
        }

        if (switchVals.containsKey(currSwitchName)) {
            throw new DukeHelpException("Multiple values supplied for switch '" + currSwitchName + "'!", currCommand);
        } else {
            if (switchMap.get(currSwitchName).argLevel == ArgLevel.NONE) {
                switchVals.put(currSwitchName, null);
            }
            elementBuilder.setLength(0); //clear elementBuilder
        }
    }

    private void checkArgAllowed() throws DukeHelpException {
        if (currSwitchName == null) { //argument not read yet
            if (currCommand.getCmdArgLevel() == ArgLevel.NONE) {
                throw new DukeHelpException("This command should not have an argument!", currCommand);
            }
        } else if (switchMap.get(currSwitchName).argLevel == ArgLevel.NONE) {
                throw new DukeHelpException("The switch '" + currSwitchName + "' should not have an argument!",
                        currCommand);
        }
    }

    private void checkSwitchAllowed() throws DukeHelpException {
        if (currCommand.getArg() == null && currCommand.getCmdArgLevel() == ArgLevel.REQUIRED) {
            throw new DukeHelpException("This command must have an argument!", currCommand);
        }
        if (switchMap.get(currSwitchName).argLevel == ArgLevel.REQUIRED) {
            throw new DukeHelpException("The switch '" + currSwitchName + "' must have an argument!",
                    currCommand);
        }
    }

    private void checkCommandValid() throws DukeException {
        if (currCommand.getCmdArgLevel() == ArgLevel.REQUIRED && currCommand.getArg() == null) {
            throw new DukeHelpException("You need to give an argument for the command!", currCommand);
        }
        for (HashMap.Entry<String, Switch> switchEntry : switchMap.entrySet()) {
            Switch checkSwitch = switchEntry.getValue();
            if (!checkSwitch.isOptional && !switchVals.containsKey(checkSwitch.name)) {
                throw new DukeHelpException("You need to give me the '" + switchEntry.getKey() + "' switch: ",
                        currCommand);
            }
        }
    }

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
