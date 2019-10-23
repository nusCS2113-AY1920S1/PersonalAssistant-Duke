package duke.command;

import duke.storage.Storage;
import duke.task.PriorityList;
import duke.task.TaskList;
import duke.ui.Ui;

//@@author talesrune
/**
 * Representing a command that deletes a task.
 */
public class DeleteCommand extends Command {
    protected int index;

    /**
     * Creates a command with the specified index.
     *
     * @param index The index of the task to be deleted.
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Executes a command that deletes the task from the task list and outputs the result.
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is deleted successfully.
     */
    @Override
    public void execute(TaskList items, Ui ui) {
        String deletedStr = "       " + items.get(index).toString();
        items.remove(index);
        ui.showDelete(items, deletedStr);
    }

    /**
     * Executes a command that deletes the task from the task list together with its priority.
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is deleted successfully.
     * @param priorities The list of priorities.
     */
    @Override
    public void execute(TaskList items, PriorityList priorities, Ui ui) {
        String deletedStr = "       " + items.get(index).toString();
        items.remove(index);
        priorities.remove(index);
        ui.showDelete(items, deletedStr);
    }

    /**
     * Executes a command that deletes the task from the task list and outputs the result (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is deleted successfully.
     * @return String to be outputted to the user.
     */
    @Override
    public String executeGui(TaskList items, Ui ui) {
        String deletedStr = "       " + items.get(index).toStringGui();
        items.remove(index);
        String str = Ui.showDeleteGui(items, deletedStr);
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
