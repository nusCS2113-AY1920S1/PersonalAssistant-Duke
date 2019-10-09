package duke.command;

import duke.exception.DukeException;
import duke.exception.DukeHelpException;

import java.util.HashMap;

public class Parser {

    enum ParseState {
        EMPTY, //not parsing anything currently
        ARG, //parsing a single-word argument for a switch or the command itself
        STRING, //parsing a quoted string
        SWITCH //parsing a switch name
    }

    private final HashMap<String, Cmd> commandMap = new HashMap<String, Cmd>();
    private ArgCommand currCommand;
    private StringBuilder elementBuilder;
    private ParseState state;
    private String currSwitch;
    private boolean isEscaped;
    private HashMap<String, ArgLevel> switches;
    private HashMap<String, String> switchVals;

    /**
     * Constructs a new Parser, generating a HashMap from Cmd enum values to allow fast lookup of command types.
     */
    public Parser() {
        for (Cmd cmd : Cmd.values()) {
            commandMap.put(cmd.toString(), cmd);
        }
    }

    /**
     * Creates a new Command of the type requested by the user, and extracts the necessary data for the command from
     * the arguments.
     *
     * @param inputStr The input to the command line
     * @return A new instance of the Command object requested
     * @throws DukeException If there is no matching command or the arguments do not meet the command's requirements.
     */
    public Command parse(String inputStr) throws DukeException {
        inputStr = inputStr.replace("\t", "    "); //sanitise input
        int firstSpaceIdx = inputStr.indexOf(" "); //index of first space
        String cmdStr = (firstSpaceIdx == -1) ? inputStr : inputStr.substring(0, firstSpaceIdx); //extract command name
        Cmd cmd = commandMap.get(cmdStr);
        if (cmd == null) {
            throw new DukeException("I'm sorry, but I don't know what that means!");
        }
        Command command = cmd.getCommand();
        // TODO: if possible, disambiguate using functions
        // trim command and first space after it from input if needed
        if (command instanceof ArgCommand) { // stripping not required otherwise
            currCommand = (ArgCommand) command;
            parseArgument(inputStr.substring(cmdStr.length()).strip());
        }
        return command;
    }

    //parsing assumes no line separators (should have been weeded out by CLI)

    /**
     * Parses the user's input and loads the parameters for this Command from it.
     *
     * @param inputStr The input provided by the user for this command, without the command keyword and stripped.
     * @throws DukeException If input was in the wrong format, contained invalid values, or was otherwise unable to be
     *                       parsed.
     */
    private void parseArgument(String inputStr) throws DukeException {
        if (inputStr.length() == 0) {
            throw new DukeException(currCommand.getEmptyArgMsg());
        }

        state = ParseState.EMPTY;
        currSwitch = null;
        switches = currCommand.getSwitches();
        elementBuilder = new StringBuilder();
        isEscaped = false;

        //FSM :D
        for (int i = 0; i < inputStr.length(); ++i) {
            char curr = inputStr.charAt(i);
            switch(state) {
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
            }
        }
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
        case ' ': //skip spaces
            break;
        default:
            checkInputAllowed();
            state = ParseState.ARG;
            break;
        }
    }

    private void handleArg(char curr) throws DukeHelpException {
        switch (curr) {
        case '-':
            throw new DukeHelpException("Invalid hyphen in argument!", currCommand);
        case '"':
            throw new DukeHelpException("Invalid quotation mark in argument!", currCommand);
        case ' ':
            writeElement();
            break;
        default:
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
        case '-':
            throw new DukeHelpException("Invalid hyphen in argument!", currCommand);
        case '"':
            throw new DukeHelpException("Invalid quotation mark in argument!", currCommand);
        case ' ':
            writeElement();
            break;
        default:
            elementBuilder.append(curr);
            break;
        }
    }

    //precondition: if not null currSwitch does not have a switchVal entry
    //precondition: either currSwitch is not null or command.arg is null
    private void writeElement() throws DukeHelpException {
        if (currSwitch != null) {
            switchVals.put(currSwitch, elementBuilder.toString());
        } else if (currCommand.arg == null) {
            currCommand.arg = elementBuilder.toString();
        }
    }

    private void checkInputAllowed() throws DukeHelpException {
        if (currSwitch == null && currCommand.arg != null) {
            throw new DukeHelpException("Multiple arguments supplied!", currCommand);
        }
    }

    private void checkMissingSwitches() throws DukeException {
        for (HashMap.Entry<String, ArgLevel> switchEntry : switches.entrySet()) {
            if (switchEntry.getValue() == ArgLevel.REQUIRED
                    && switchVals.get(switchEntry.getKey()) == null) {
                throw new DukeHelpException("You need to give me this switch: "
                        + switchEntry.getKey(), currCommand);
            }
        }
    }
}
