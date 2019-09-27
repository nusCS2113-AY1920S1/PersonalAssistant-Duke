package duke.command;

import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

import java.io.IOException;

/**
 * Representing a command that states a task is duplicated.
 */
public class DuplicateFoundCommand extends Command{
    /**
     * Outputs the alert when duplicated task is detected.
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is marked as done successfully.
     */
    @Override
    public void execute(TaskList items, Ui ui) {
        ui.showDuplicateMsg();
    }

    /**
     * Executes a command that marks the task as done in the task list and outputs the result (GUI).
     * (Not in use)
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is marked as done successfully.
     * @return String to be outputted to the user.
     */
    @Override
    public String executeGui(TaskList items, Ui ui) {
        return "";
    }

    /**
     * Executes a command that overwrites existing storage with the updated task list.
     * (Not in use)
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is executed successfully.
     * @param storage The storage to be overwritten.
     */
    @Override
    public void executeStorage(TaskList items, Ui ui, Storage storage) throws IOException {
    }
}