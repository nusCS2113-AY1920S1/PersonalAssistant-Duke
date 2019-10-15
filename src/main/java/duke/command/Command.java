package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

/**
 * Highest-level abstract class for Command objects.
 */
public abstract class Command {
    /**
     * Parses the user's input and loads the parameters for this Command from it.
     *
     * @param inputStr The input provided by the user for this command, without the command keyword and stripped.
     * @throws DukeException If input was in the wrong format, contained invalid values, or was otherwise unable to be
     *                       parsed.
     */
    public void parse(String inputStr) throws DukeException {
        //TODO
    }

    /**
     * Runs the command using the parameters loaded with Command's parse method.
     *
     * @param core The DukeCore object for this command to operate on.
     * @throws DukeException If command fails to execute.
     * @see DukeCore
     */
    public abstract void execute(DukeCore core) throws DukeException;

    /**
     * Runs the command using the parameters loaded with Command's parse method, but without any changes to storage and
     * returning output that would ordinarily be printed, although not necessarily in the same format.
     *
     * @param core The DukeCore object for this command to operate on.
     * @return String containing output would be printed during ordinary execution.
     * @throws DukeException If command fails to execute.
     * @see DukeCore
     */
    public String silentExecute(DukeCore core) throws DukeException {
        return null;
    }
}
