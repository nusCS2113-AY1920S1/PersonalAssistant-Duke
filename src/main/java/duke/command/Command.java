package duke.command;

import duke.commons.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Represents a duke.command that can be executed by the user.
 */
public abstract class Command {

    /**
     * Execute the duke.command.
     *
     * @param tasks   A TaskList containing all tasks.
     * @param storage A Storage object which specifies the location of the data.
     * @param ui A Ui object capble of controlling GUI.
     * @throws DukeException If the execution fails.
     */
    abstract public void execute(TaskList tasks, Storage storage, Ui ui) throws DukeException;
}
