package duke.command;

import duke.DukeContext;
import duke.exception.DukeException;

import java.util.HashMap;

/**
 * Abstract class for commands that involve an argument.
 */
public abstract class ArgCommand extends Command {
    protected String arg; //argument supplied to the command
    protected String emptyArgMsg; //error message if the argument is empty
    protected String[] switches; //list of recognised switches
    protected HashMap<String, String> switchVals; //hashmap of parameters

    /**
     * Parses the user's input and loads the parameters for this Command from it.
     *
     * @param inputStr The input provided by the user for this command, without the command keyword and stripped.
     * @throws DukeException If input was in the wrong format, contained invalid values, or was otherwise unable to be
     *                       parsed.
     */
    public void parse(String inputStr) throws DukeException {
        arg = inputStr.strip();
        if (arg.length() == 0) {
            throw new DukeException(emptyArgMsg);
        }
        int spaceIdx = inputStr.indexOf(" ");
        int dashIdx = inputStr.indexOf("-");
        int quoteIdx = inputStr.indexOf("\"");
        while (spaceIdx >= 0) {
            spaceIdx = inputStr.indexOf(" ", spaceIdx + 1);
            dashIdx = inputStr.indexOf("-", spaceIdx);
            quoteIdx = inputStr.indexOf("\"", spaceIdx);
        }
    }

    @Override
    public void execute(DukeContext ctx) throws DukeException, DukeException {
        if (arg == null) {
            throw new DukeException("Command needs to parse argument first!");
        }
    }
}
