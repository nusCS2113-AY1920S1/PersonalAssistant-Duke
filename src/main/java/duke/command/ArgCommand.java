package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

/**
 * Abstract class for commands that involve an argument.
 */
public abstract class ArgCommand extends Command {
    protected String arg; //argument supplied to the command
    protected String emptyArgMsg; //error message if the argument is empty

    /**
     * Parse the input string entered by the user.
     *
     * @param inputStr The input provided by the user for this command, without the command keyword and stripped.
     * @throws DukeException If the input string entered is empty.
     */
    public void parse(String inputStr) throws DukeException {
        arg = inputStr.strip();
        if (arg.length() == 0) {
            throw new DukeException(emptyArgMsg);
        }
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        if (arg == null) {
            throw new DukeException("Command needs to parse argument first!");
        }
    }
}
