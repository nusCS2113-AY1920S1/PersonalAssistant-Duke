package command;

import exception.DukeException;
import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * Represents a command from user to delete a task.
 * Inherits from Command class.
 * @author Zhang Yue Han
 */
public class DeleteCommand extends Command {

    /**
     * The index of the task to be deleted which is known throughout this class.
     */
    protected int index;

    /**
     * Assigns the index to take on the value of input number-1 from user.
     * @param num the index of the task specified by the user
     */
    public DeleteCommand(int num) {
        index = num - 1;
    }

    /**
     * Deletes task item from the task list, removes the specified task from hard disk
     * and prints feedback to the user.
     * @param ui Ui object used to display information to the user
     * @param tasks TaskList object which contains the task array list holding the task info
     * @param storage Storage object which is used to remove specified task to file
     */
    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage) {
        //ask ui to print something
        //ask tasks to store the thing in arraylist
        //ask storage to write to file
//        try {
//            if (index >= tasks.getSize() || tasks.getList().isEmpty()) {
//                throw new DukeException(DukeException.ErrorType.INDEX_EXCEEDED);
//            }
//            ui.showDeleted(tasks.getTaskItem(index));
//            storage.deleteItemFromFile(tasks.getItemData(index), "src/main/data/duke.txt");
//            tasks.deleteItem(index);
//        } catch (DukeException e) {
//            e.showError();
//        }
    }

}
