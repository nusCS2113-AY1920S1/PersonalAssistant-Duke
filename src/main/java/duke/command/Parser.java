package duke.command;

import duke.exception.DukeException;
import duke.exception.DukeHelpException;

import java.util.HashMap;

public class Parser {
    private final HashMap<String, Cmd> commandMap = new HashMap<String, Cmd>();

    enum ParseState {
        EMPTY, //not parsing anything currently
        ARG, //parsing a single-word argument for a switch or the command itself
        STRING, //parsing a quoted string
        SWITCH //parsing a switch name
    }

    /**
     *  Inner class to hold variables required for current parse.
     */
    static class currParse {
        //man I miss structs
        static ArgCommand command;
        static StringBuilder elementBuilder;
        static ParseState state;
        static String currSwitch;
        static HashMap<String, ArgLevel> switches;
        static HashMap<String, String> switchVals;
    }

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
            currParse.command = (ArgCommand) command;
            parseArgument(inputStr.substring(cmdStr.length()).strip(), currParse.command);
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
    private void parseArgument(String inputStr, ArgCommand command) throws DukeException {
        if (inputStr.length() == 0) {
            throw new DukeException(command.getEmptyArgMsg());
        }

        currParse.state = ParseState.EMPTY;
        currParse.currSwitch = null;
        currParse.switches = command.getSwitches();
        currParse.command = command;
        currParse.elementBuilder = new StringBuilder();

        //FSM :D
        for (int i = 0; i < inputStr.length(); ++i) {
            char curr = inputStr.charAt(i);
            switch(currParse.state) {
            case EMPTY:
                handleEmpty(inputStr, i, curr);
                break;
            case ARG:
                handleArg(inputStr, i, curr);
                break;
            case STRING:
                handleString(inputStr, i, curr);
                break;
            case SWITCH:
                handleSwitch(inputStr, i, curr);
                break;
            }
        }
    }

    private void handleEmpty(String inputStr, int i, char curr) throws DukeHelpException {
        switch (curr) {
        case '-':
            currParse.state = ParseState.SWITCH;
            break;
        case '"':
            currParse.state = ParseState.STRING;
            break;
        case ' ': //skip spaces
            break;
        default:
            if (currParse.command.arg != null) {
                throw new DukeHelpException("Multiple command arguments given!", currParse.command);
            } else {
                currParse.state = ParseState.ARG;
            }
            break;
        }
    }

    private void handleArg(String inputStr, int i, char curr) throws DukeHelpException {
        switch (curr) {

        }
    }

    private void handleString(String inputStr, int i, char curr) throws DukeHelpException {
        switch (curr) {

        }
    }

    private void handleSwitch(String inputStr, int i, char curr) throws DukeHelpException {
        switch (curr) {

        }
    }

    private void checkMissingSwitches() throws DukeException {
        for (HashMap.Entry<String, ArgLevel> switchEntry : currParse.switches.entrySet()) {
            if (switchEntry.getValue() == ArgLevel.REQUIRED
                    && currParse.switchVals.get(switchEntry.getKey()) == null) {
                throw new DukeHelpException("You need to give me this switch: "
                        + switchEntry.getKey(), currParse.command);
            }
        }
    }
}
