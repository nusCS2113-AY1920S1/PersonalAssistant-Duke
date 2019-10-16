package command;

import exception.DukeException;
import task.Task;
import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * The DeleteCommand class is used when the user intends to delete a particular
 * task from their task list.
 *
 * @author Sai Ganesh Suresh
 * @version v1.0
 */
public class DeleteCommand extends Command {

    private int indexOfTask;

    public DeleteCommand(int indexOfTask) {
        this.indexOfTask = indexOfTask;
    }

    /**
     * Used to add the respective tasks to the TaskList and save to persistent
     * storage.
     *
     * @param tasks   this string holds command type determinant to decide how to
     *                process the user input.
     * @param storage this parameter provides the execute function the storage to
     *                allow the saving of the file.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) throws DukeException {
        if (indexOfTask >= 0 && indexOfTask <= (tasks.getSize() - 1)) {
            Task task = tasks.delete(indexOfTask);
            storage.saveFile(tasks.getTasks());
            Ui.printOutput("Noted. I've removed this task:" + "\n " + task.toString() + "\nNow you have "
                + tasks.getSize() + " task(s) in the list.");
        } else {
            throw new DukeException(DukeException.taskDoesNotExist());
        }
    }
}