package duke.command;

import duke.DukeContext;
import duke.exception.DukeException;

/**
 * Highest-level abstract class for Command objects.
 */
public abstract class Command {
    /**
     * Parses the user's input and loads the parameters for this Command from it.
     * @param inputStr The input provided by the user for this command, without the command keyword and stripped.
     * @throws DukeException If input was in the wrong format, contained invalid values, or was otherwise unable to be
     * parsed.
     */
    public void parse(String inputStr) throws DukeException {}

    /**
     * Runs the command using the parameters loaded with Command's parse method.
     * @param ctx The DukeContext object for this command to operate on.
     * @throws DukeException If command fails to execute.
     * @see DukeContext
     */
    public abstract void execute(DukeContext ctx) throws DukeException;
}
