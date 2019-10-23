package command;

import exception.DukeException;
import task.Task;
import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * Allows the user to delete a particular task from their task list based on index.
 *
 * @author Sai Ganesh Suresh
 * @version v1.3
 */
public class DeleteCommand extends Command {

    private Integer indexOfTask;

    public DeleteCommand(Integer indexOfTask) {
        this.indexOfTask = indexOfTask;
    }

    /**
     * Removes the task from the TaskList and saves the updated TaskList to persistent storage.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) throws DukeException {
        if (isIndexValid(indexOfTask, tasks.getSize())) {
            Task task = tasks.delete(indexOfTask);
            storage.saveFile(tasks.getTasks());
            Ui.printOutput("Noted. I've removed this task:" + "\n " + task.toString() + "\nNow you have "
            + tasks.getSize() + " task(s) in the list.");
        }
    }
}