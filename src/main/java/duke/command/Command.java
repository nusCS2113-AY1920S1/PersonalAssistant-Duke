package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

/**
 * Highest-level abstract class for Command objects.
 */
public abstract class Command {

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

    //TODO: replace with abstract function that actually prints excerpts from the user guide
    public String getHelp() {
        return "https://github.com/AY1920S1-CS2113-T14-1/main/blob/master/docs/UserGuide.adoc";
    }
}
