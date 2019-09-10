import java.util.*;
import java.io.*;
import java.text.*;

/**
 * Carries out the necessary operations to delete a Task from the TaskList, when
 * supplied the index number of that Task in the TaskList.
 */

public class DeleteCommand extends Command {
    protected int taskNo;

    /**
     * Constructor for a DeleteCommand.
     * @param taskNo the index number of the Task to be deleted.
     */
    public DeleteCommand(String taskNo) {
        this.taskNo = Integer.parseInt(taskNo) - 1;
    }

    /**
     * This method will check if the task number is valid, and then delete the Task
     * whose index number matches the number provided by the user.
     * @param ui An instance of the user interface.
     * @param storage An instance of the Storage class.
     * @param tasks The TaskList, containing all the created Tasks thus far.
     * @throws DukeException if the number provided by the user is invalid.
     */
    public void execute(Ui ui, Storage storage, TaskList tasks) throws DukeException {
        if (taskNo + 1 > tasks.getNumberOfItems()) {
            throw new DukeException("We don't have that many tasks!");
        }
        Task thisTask = tasks.getItem(taskNo);
        tasks.deleteItem(taskNo);
        ui.printRemovedTask(thisTask.toString(), tasks.getNumberOfItems());
    }
}