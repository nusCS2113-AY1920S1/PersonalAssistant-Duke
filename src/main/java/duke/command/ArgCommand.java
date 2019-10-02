package duke.command;

import duke.DukeContext;
import duke.exception.DukeException;

import java.util.HashMap;

/**
 * Abstract class for commands that involve an argument.
 */
public abstract class ArgCommand extends Command {

    protected String arg = null; //argument supplied to the command
    protected String emptyArgMsg; //error message if the argument is empty
    protected HashMap<String, String> switchVals; //hashmap of switch parameters
    protected HashMap<String, ArgLevel> switches; //list of recognised switches

    /**
     *  Inner class to hold indices of tokens particular tokens when parsing an input string.
     */
    static class Tokens {
        //man I miss structs
        static int spaceIdx, dashIdx, quoteIdx, endQuoteIdx;
        static String currSwitch;
    }

    /**
     * Parses the user's input and loads the parameters for this Command from it.
     *
     * @param inputStr The input provided by the user for this command, without the command keyword and stripped.
     * @throws DukeException If input was in the wrong format, contained invalid values, or was otherwise unable to be
     *                       parsed.
     */
    public void parse(String inputStr) throws DukeException {
        if (inputStr.length() == 0) {
            throw new DukeException(emptyArgMsg);
        }

        //find initial tokens


        while (Tokens.spaceIdx >= 0) {
            if (Tokens.quoteIdx < Tokens.spaceIdx) {

            }
        }
    }

    @Override
    public void execute(DukeContext ctx) throws DukeException, DukeException {
        if (arg == null) {
            throw new DukeException("Command needs to parse argument first!");
        }
    }

    protected void complain(String complaint, String ...issues) throws DukeException {
        StringBuilder complaintBuilder = new StringBuilder(complaint).append(": ");
        for (String issue : issues) {
            complaintBuilder.append(issue)
        }
       throw new DukeException(complaint + ": ")
    }

    private void initTokens(String inputStr) {
        Tokens.spaceIdx = inputStr.indexOf(" ");
        Tokens.dashIdx = inputStr.indexOf("-");
        Tokens.quoteIdx = inputStr.indexOf("\"");
        Tokens.endQuoteIdx = inputStr.indexOf("\"", Tokens.quoteIdx + 1);
        Tokens.currSwitch = null;

        if (Tokens.spaceIdx == -1 && switches.size() != 0) {
            //search pairs instead and complain with names
            for (ArgLevel level : switches.values()) {
                if (level != ArgLevel.NONE) {
                }
            }
        }
    }
}
