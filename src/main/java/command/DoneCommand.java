package command;

import exception.DukeException;
import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * Represents a command from user to mark a task as done.
 * Inherits from Command class.
 * @author Zhang Yue Han
 */

public class DoneCommand extends Command {

    /**
     * The index of the task to be marked done which is known throughout this class.
     */
    protected int index;

    /**
     * Assigns the index to take on the value of input number-1 from user.
     * @param num the index of the task specified by the user
     */
    public DoneCommand(int num) {
        index = num - 1;
    }

    /**
     * Marks item as done
     * @param ui Ui object used to display information to the user
     * @param tasks TaskList object which contains the task array list holding the task info
     * @param storage Storage object which is used to write task changes to file
     */
    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage) {
        //ask ui to print something
        //ask tasks to store the thing in arraylist
        //ask storage to write to file
//        try {
//            if (index > tasks.getSize() || tasks.getList().isEmpty()) {
//                throw new DukeException(DukeException.ErrorType.INDEX_EXCEEDED);
//            }
            tasks.markItemDone(index);
            storage.writeFile(tasks.getArrayData(), false, "src/main/data/duke.txt");
            ui.showDone(tasks.getTaskItem(index));
//        } catch (DukeException e) {
//            e.showError();
//        }
    }
}
