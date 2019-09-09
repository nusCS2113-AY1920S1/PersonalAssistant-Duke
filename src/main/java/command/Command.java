package command;

import commons.DukeException;
import storage.Storage;
import task.TaskList;

/**
 * Represents a command that can be executed by the user.
 */
public abstract class Command {
    /**
     * Execute the command.
     *
     * @param tasks   A TaskList containing all tasks.
     * @param storage A Storage object which specifies the location of the data.
     * @throws DukeException If the execution fails.
     */
    abstract public void execute(TaskList tasks, Storage storage) throws DukeException;
}
