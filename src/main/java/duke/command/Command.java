package duke.command;

import duke.exception.DukeException;
import duke.Storage;
import duke.TaskList;
import duke.Ui;

/**
 * Represents a <code>Command</code> object that corresponds to specific commands
 * defined by the user.
 * Abstract parent of all other <code>Command</code> subclasses.
 */
public abstract class Command {
    /**
     * Invokes other <code>Command</code> subclasses based on the input given by the user.
     * @param tasks Instance of <code>TaskList</code> that stores <code>Task</code> objects.
     * @param ui Instance of <code>Ui</code> that is responsible for visual feedback.
     * @param storage Instance of <code>Storage</code> that enables the reading and writing of <code>Task</code>
     *      *         objects to harddisk.
     * @throws DukeException Catches invalid commands given by user.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException;
    /**
     * Checks if <code>exitCommand</code> is called for <code>Duke</code>
     * to terminate.
     * @return true if <code>exitCommand</code> is called, false otherwise.
     */
    public abstract boolean isExit();
}