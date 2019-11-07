package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

/**
 * Highest-level abstract class for Command objects.
 */
public class Command {

    protected final CommandSpec spec;

    public Command(CommandSpec spec) {
        this.spec = spec;
    }

    /**
     * Runs the command using the parameters loaded with Command's parse method.
     *
     * @param core The DukeCore object for this command to operate on.
     * @throws DukeException If command fails to execute.
     * @see DukeCore
     */
    public void execute(DukeCore core) throws DukeException {
        spec.execute(core, this);
    }

    //TODO: replace with abstract function that actually prints excerpts from the user guide
    public String getHelp() {
        return spec.getHelp();
    }
}
