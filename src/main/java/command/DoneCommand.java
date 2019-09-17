package command;

import exception.DukeException;
import task.Task;
import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * The DoneCommand class is used when the user intends to mark a task as done.
 *
 * @author Sai Ganesh Suresh
 * @version v1.0
 */
public class DoneCommand extends Command {
    private int indexOfTask;

    public DoneCommand(int indexOfTaskIndex) {
        this.indexOfTask = indexOfTaskIndex;
    }

    /**
     * This execute function is used to add the respective tasks to the TaskList and save to persistent storage.
     *
     * @param tasks this string holds command type determinant to decide how to process the user input.
     * @param ui this string holds the description of the task provided by the user.
     * @param storage this parameter provides the execute function the storage to allow the saving of the file.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (indexOfTask < 0 || indexOfTask > (tasks.getSize() - 1)) {
            throw new DukeException(DukeException.TASK_DOES_NOT_EXIST());
        }

        Task task = tasks.markAsDone(indexOfTask);
        storage.saveFile(tasks.getTasks());

        Ui.printMessage("Nice! I've marked this task as done:");
        Ui.printMessage("  " + task.toString());
    }
}

