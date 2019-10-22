package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

/**
 * Class responsible for executing user commands.
 */
public class Executor {
    private DukeCore core;

    /**
     * Constructs an Executor object with the defined core.
     *
     * @param core Core of Dr. Duke.
     */
    public Executor(DukeCore core) {
        this.core = core;
    }

    /**
     * Executes the specified command.
     *
     * @param command Command object.
     * @throws DukeException If there is an error executing the command.
     */
    public void execute(Command command) throws DukeException {
        command.execute(core);
    }
}
