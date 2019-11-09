package duke.command;

import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

//@@author gervaiseang

/**
 *  Represents a command to set reminders to tasks.
 */
public class RemindCommand extends Command {

    /**
     * Executes a command that gathers all tasks from task list and outputs the result (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user the list of tasks stored in task list.
     * @return List of tasks.
     */
    @Override
    public String executeGui(TaskList items, Ui ui) { return ""; }

    /**
     * Executes a command that overwrites existing storage with the updated task list.
     * (Not in use)
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is executed successfully.
     * @param storage The storage to be overwritten.
     */
    @Override
    public void executeStorage(TaskList items, Ui ui, Storage storage) {
    }
}
