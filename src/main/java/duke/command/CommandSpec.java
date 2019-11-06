package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

public abstract class CommandSpec {

    protected Command cmd;

    /**
     * Set {@code cmd} to the supplied command, exposing access to the switches supplied to it, and perform the
     * operations specified by this CommandSpec object's {@code execute(DukeCore core)} method.
     *
     * @param core The DukeCore object to use to execute the command.
     * @param cmd The command instance being executed.
     * @see DukeCore
     * @see Command
     * @throws DukeException If an error occurs during command execution.
     */
    public void execute(DukeCore core, Command cmd) throws DukeException {
        this.cmd = cmd;
        execute(core);
        this.cmd = null;
    }

    protected abstract void execute(DukeCore core) throws DukeException;

    /**
     * Returns a string describing the operation and effect of this command.
     * @return The help string for this command, typically an excerpt of the user guide.
     */
    public String getHelp() {
        return "https://github.com/AY1920S1-CS2113-T14-1/main/blob/master/docs/UserGuide.adoc";
    }
}
