package command;

import exception.DukeException;
import task.Task;
import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * Marks a task as complete or done.
 *
 * @author Sai Ganesh Suresh
 * @version v1.3
 */
public class DoneCommand extends Command {
    private int indexOfTask;

    public DoneCommand(int indexOfTaskIndex) {
        this.indexOfTask = indexOfTaskIndex;
    }

    /**
     * Marks a task as complete and saves the updated TaskList to persistent storage.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     */
    public void execute(TaskList tasks, Storage storage) throws DukeException {
        if (isIndexValid(indexOfTask, tasks.getSize())) {
            Task task = tasks.markAsDone(indexOfTask);
            storage.saveFile(tasks.getTasks());
            Ui.printOutput("Nice! I've marked this task as done: " + task.toString());
        }
    }
}
