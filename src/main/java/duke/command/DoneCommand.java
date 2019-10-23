package duke.command;

import duke.storage.Storage;
import duke.ui.Ui;
import duke.task.TaskList;

//@@author talesrune
/**
 * Representing a command that marks a task as done.
 */
public class DoneCommand extends Command {
    protected final int index;

    /**
     * Creates a command with the specified index.
     *
     * @param index The index of the task to be marked as done.
     */
    public DoneCommand(int index) {
        this.index = index;
    }

    /**
     * Executes a command that marks the task as done in the task list and outputs the result.
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is marked as done successfully.
     */
    @Override
    public void execute(TaskList items, Ui ui) {
        items.get(index).setStatusIcon(true);
        ui.showDone(items, index);
    }

    /**
     * Executes a command that marks the task as done in the task list and outputs the result (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is marked as done successfully.
     * @return String to be outputted to the user.
     */
    @Override
    public String executeGui(TaskList items, Ui ui) {
        items.get(index).setStatusIcon(true);
        String str = Ui.showDoneGui(items, index);
        return str;
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
    public void executeStorage(TaskList items, Ui ui, Storage storage) {

    }
}
