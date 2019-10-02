package duke.command;

import duke.exception.DukeException;

import java.util.HashMap;

public class Parser {
    private final HashMap<String, Cmd> commandMap = new HashMap<String, Cmd>();

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
            inputStr = inputStr.substring(cmdStr.length()).strip();
            ((ArgCommand) command).parse(inputStr);
        }
        return command;
    }
}
