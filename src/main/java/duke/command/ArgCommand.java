package duke.command;

import duke.DukeContext;
import duke.exception.DukeException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Abstract class for commands that involve an argument.
 */
public abstract class ArgCommand extends Command {

    protected String arg = null; //argument supplied to the command itself, not its flags
    protected boolean requiresArg; //whether the command itself requires an argument, or just flags
    protected String emptyArgMsg; //error message if the argument is missing
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
            throw new DukeException("You didn't tell me how to do that!");
            //TODO: print help passage, possibly with a "HelpDukeException"?
        }

        //find initial tokens
        initTokens(inputStr);

        while (Tokens.spaceIdx >= 0) {
            //string encountered
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

    protected void complain(String complaint, ArrayList<String> issues) throws DukeException {
        if (issues.size() == 0) { //shouldn't happen
            throw new DukeException(complaint + ": no details specified!");
        }
        StringBuilder complaintBuilder = new StringBuilder(complaint).append(": ").append(issues.get(0));
        for (int i = 1; i < issues.size(); ++i) {
            complaintBuilder.append(", ").append(issues.get(i));
        }
        throw new DukeException(complaintBuilder.append(System.lineSeparator()).toString());
    }

    private void initTokens(String inputStr) throws DukeException {
        Tokens.spaceIdx = inputStr.indexOf(" ");
        Tokens.dashIdx = inputStr.indexOf("-");
        Tokens.quoteIdx = inputStr.indexOf("\"");
        Tokens.endQuoteIdx = inputStr.indexOf("\"", Tokens.quoteIdx + 1);
        Tokens.currSwitch = null;

        //TODO: refactor out when usage is clearer
        //handle single word arguments
        if (Tokens.spaceIdx == -1) {
            if (Tokens.dashIdx == 0) {
               if (switches.get(inputStr.substring(Tokens.dashIdx + 1)) == null) {

               }
            } else if {

            }

            if ()

            if (switches.size() != 0) {
                //search pairs instead and complain with names
                ArrayList<String> problemArrList = new ArrayList<String>();
                for (HashMap.Entry<String, ArgLevel> switchEntry : switches.entrySet()) {
                    if (switchEntry.getValue() != ArgLevel.NONE) {
                        problemArrList.add(switchEntry.getKey());
                    }
                }
                if (problemArrList.size() != 0) {
                    complain("You didn't give me these switches", problemArrList);
                }
            }
        }
    }
}
