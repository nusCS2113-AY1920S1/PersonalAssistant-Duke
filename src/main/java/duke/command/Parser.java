package duke.command;

import duke.exception.DukeException;

import java.util.HashMap;

public class Parser {
    private final HashMap<String, Cmd> commandMap = new HashMap<String, Cmd>();

    /**
     *  Inner class to hold variables required for current parse.
     */
    static class currParse {
        //man I miss structs
        static int spaceIdx, dashIdx, quoteIdx, endQuoteIdx;
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
            parseArgument(inputStr.substring(cmdStr.length()).strip(), (ArgCommand) command);
        }
        return command;
    }

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

        //find initial tokens
        currParse.switches = command.getSwitches();
        initParse(inputStr);

        while (currParse.spaceIdx >= 0) {
            if (currParse.quoteIdx < currParse.spaceIdx) {

            }
        }
    }

    private void initParse(String inputStr) {
        currParse.spaceIdx = inputStr.indexOf(" ");
        currParse.dashIdx = inputStr.indexOf("-");
        currParse.quoteIdx = inputStr.indexOf("\"");
        currParse.endQuoteIdx = inputStr.indexOf("\"", currParse.quoteIdx + 1);
        currParse.currSwitch = null;

        if (currParse.spaceIdx == -1 && currParse.switches.size() != 0) {
            //find missing switch
            for (HashMap.Entry<String, ArgLevel> switchEntry : currParse.switches.entrySet()) {
                if (switchEntry.getValue() == ArgLevel.REQUIRED
                        && currParse.switchVals.get(switchEntry.getKey()) == null) {

                }
            }
        }
    }

    protected void complain(String complaint, String ...issues) throws DukeException {
        StringBuilder complaintBuilder = new StringBuilder(complaint).append(": ");
        for (String issue : issues) {
            complaintBuilder.append(issue)
        }
        throw new DukeException(complaint + ": ")
    }
}
