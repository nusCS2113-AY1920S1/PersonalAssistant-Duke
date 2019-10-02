package command;

import exception.DukeException;
import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * Represents a command from user to lists current tasks.
 * Inherits from Command class.
 */
public class ListCommand extends Command {

    /**
     * Prints the current task list.
     * @param ui Ui object used to display information to the user
     * @param tasks TaskList object which contains the task array list holding the task info
     * @param storage Storage object which is used to write new task to file
     */
    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage) {
        //ask ui to print something
        //ask tasks to store the thing in arraylist
        //ask storage to write to file
        try {
            if (tasks.getList().isEmpty()) {
                throw new DukeException(DukeException.ErrorType.LIST_EMPTY);
            }
            ui.showList(tasks);
        } catch (DukeException e) {
            e.showError();
        }
    }
}
