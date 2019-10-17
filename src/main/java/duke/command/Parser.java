package duke.command;

import duke.exception.DukeException;
import duke.exception.DukeHelpException;

import java.util.HashMap;
import java.util.Map;

import static duke.command.Parser.ParseState.EMPTY;
import static java.lang.Math.min;

/**
 * Class for parsing user input from the UI in order to construct Commands of the appropriate type and parameters,
 * and execute them. The static initializer generates a static map from Cmd enum values to allow fast lookup of
 * command types.
 */
@SuppressWarnings("unchecked") //unchecked assignment used to initialize static command map
public class Parser {

    enum ParseState {
        EMPTY, //not parsing anything currently
        ARG, //parsing a single-word argument for a switch or the command itself
        STRING, //parsing a quoted string
        SWITCH //parsing a switch name
    }

    private static Map<String, Cmd> commandMap;
    private ArgCommand currCommand;
    private StringBuilder elementBuilder;
    private ParseState state;
    private String currSwitch;
    private boolean isEscaped;
    private Map<String, Switch> switchMap;
    private HashMap<String, String> switchVals;

    static {
        Cmd[] cmdArr = Cmd.values();
        Map.Entry[] entryArr = new Map.Entry[cmdArr.length];
        for (int i = 0; i < cmdArr.length; ++i) {
            entryArr[i] = Map.entry(cmdArr[i].toString(), cmdArr[i]);
        }
        commandMap = Map.ofEntries(entryArr);
    }

    /**
     * Creates a new Command of the type requested by the user, and extracts the necessary data for the command from
     * the arguments. Literal line separators in the text will be converted to \n for consistency.
     *
     * @param inputStr The input to the command line
     * @return A new instance of the Command object requested
     * @throws DukeException If there is no matching command or the arguments do not meet the command's requirements.
     */
    public Command parse(String inputStr) throws DukeException {
        inputStr = inputStr.replace("\t", "    "); //sanitise input
        int spaceIdx = inputStr.indexOf(" ");
        int sepIdx = inputStr.indexOf(System.lineSeparator());
        String cmdStr = inputStr;
        if (!(spaceIdx == -1 && sepIdx == -1)) {
            if (spaceIdx == -1) {
                cmdStr = inputStr.substring(0, sepIdx);
            } else if (sepIdx == -1) {
                cmdStr = inputStr.substring(0, spaceIdx);
            } else {
                cmdStr = inputStr.substring(0, min(sepIdx, spaceIdx));
            }
        }
        Cmd cmd = commandMap.get(cmdStr);
        if (cmd == null) {
            throw new DukeException("I'm sorry, but I don't recognise this command: " + cmdStr);
        }
        Command command = cmd.getCommand();
        // TODO: autocorrect system
        // trim command and first space after it from input if needed, and standardise newlines
        if (command instanceof ArgCommand) { // stripping not required otherwise
            currCommand = (ArgCommand) command;
            inputStr = inputStr.replaceAll("(\\r\\n|\\n|\\r)", "\n");
            parseArgument(inputStr.substring(cmdStr.length()));
        }
        return command;
    }

    /*public Command parse(String inputStr, DukeContext context) {

    }*/

    /**
     * Parses the user's input after the Command name and loads the parameters for the Command from it.
     *
     * @param inputStr The input provided by the user for this command, without the command keyword and stripped.
     * @throws DukeException If input was in the wrong format, contained invalid values, or was otherwise unable to be
     *                       parsed.
     */
    private void parseArgument(String inputStr) throws DukeException {
        assert (!inputStr.contains("\r"));
        if (inputStr.length() == 0) {
            throw new DukeException(currCommand.getEmptyArgMsg());
        }

        state = EMPTY;
        currSwitch = null;
        switchMap = currCommand.getSwitchMap();
        switchVals = new HashMap<String, String>();
        elementBuilder = new StringBuilder();
        isEscaped = false;

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
        case ARG:
            writeElement();
            break;
        case STRING:
            // TODO: disambiguate/autocorrect?
            throw new DukeHelpException("String in argument was not closed: " + elementBuilder.toString(),
                    currCommand);
        case SWITCH:
            addSwitch();
            break;
        default:
            throw new DukeException("Invalid parser state!");
        }

        checkCommandValid();
        currCommand.setSwitchValsMap(switchVals);
    }

    private void handleEmpty(char curr) throws DukeHelpException {
        switch (curr) {
        case '-':
            state = ParseState.SWITCH;
            break;
        case '"':
            checkInputAllowed();
            state = ParseState.STRING;
            break;
        case '\n': //fallthrough
        case ' ': //skip spaces
            break;
        default:
            checkInputAllowed();
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
            state = EMPTY;
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

    private void writeElement() {
        assert (currSwitch != null || currCommand.getArg() == null);
        if (currSwitch != null) {
            switchVals.put(currSwitch, elementBuilder.toString());
            currSwitch = null;
        } else { //currCommand.arg == null
            currCommand.setArg(elementBuilder.toString());
        }
        elementBuilder.setLength(0); //clear elementBuilder
        state = EMPTY;
    }

    // TODO: this function is going to become very big with autocorrect
    private void addSwitch() throws DukeHelpException {
        String newSwitch = elementBuilder.toString();
        if (!switchMap.containsKey(newSwitch)) {
            // TODO: call helpers
            throw new DukeHelpException("I don't know what this switch is: " + newSwitch, currCommand);
        } else if (switchVals.containsKey(newSwitch)) {
            throw new DukeHelpException("Multiple values supplied for switch: " + newSwitch, currCommand);
        } else {
            if (switchMap.get(newSwitch).argLevel != ArgLevel.NONE) {
                currSwitch = newSwitch;
            } else {
                switchVals.put(newSwitch, null);
            }
            elementBuilder.setLength(0); //clear elementBuilder
        }
    }

    private void checkInputAllowed() throws DukeHelpException {
        if (currSwitch == null) {
            if (currCommand.getArg() == null) {
                if (currCommand.getCmdArgLevel() == ArgLevel.NONE) {
                    throw new DukeHelpException("This command should not have an argument!", currCommand);
                }
            } else {
                throw new DukeHelpException("Multiple arguments supplied! You already gave: " + currCommand.getArg(),
                        currCommand);
            }
        }
    }

    private void checkCommandValid() throws DukeException {
        if (currCommand.getCmdArgLevel() == ArgLevel.REQUIRED && currCommand.getArg() == null) {
            throw new DukeHelpException("You need to give an argument for the command!", currCommand);
        }
        for (HashMap.Entry<String, Switch> switchEntry : switchMap.entrySet()) {
            if (switchEntry.getValue().argLevel == ArgLevel.REQUIRED
                    && switchVals.get(switchEntry.getKey()) == null) {
                throw new DukeHelpException("You need to give me this switch: "
                        + switchEntry.getKey(), currCommand);
            }
        }
    }
}
