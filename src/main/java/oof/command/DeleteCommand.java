package oof.command;

import oof.TaskList;
import oof.Ui;
import oof.Storage;
import oof.exception.OofException;
import oof.task.Task;

/**
 * Represents a Command to delete a specific Task.
 */
public class DeleteCommand extends Command {

    private int index;

    /**
     * Constructor for DeleteCommand.
     *
     * @param index Represents the index of the Task to be deleted.
     */
    public DeleteCommand(int index) {
        super();
        this.index = index;
    }

    /**
     * Deletes the specific Task defined by the user
     * after confirming the validity of the Command inputted by the user.
     *
     * @param arr     Instance of TaskList that stores Task objects.
     * @param ui      Instance of Ui that is responsible for visual feedback.
     * @param storage Instance of Storage that enables the reading and writing of Task
     *                objects to hard disk.
     * @throws OofException Catches invalid commands given by user.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) throws OofException {
        if (index >= arr.getSize() || index < 0) {
            throw new OofException("OOPS!!! Invalid number!");
        } else {
            Task task = arr.getTask(index);
            arr.deleteTask(index);
            ui.deleteMessage(task, arr.getSize());
            storage.writeToFile(arr);
        }
    }

    /**
     * Checks if ExitCommand is called for Oof to terminate.
     *
     * @return false.
     */
    public boolean isExit() {
        return false;
    }
}
