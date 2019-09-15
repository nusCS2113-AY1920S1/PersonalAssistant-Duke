package command;

import exception.DukeException;
import task.Task;
import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * The DeleteCommand class is used when the user intends to delete a particular task from their task list.
 *
 * @author Sai Ganesh Suresh
 * @version v1.0
 */
public class DeleteCommand extends Command{

    private int indexOfTask;

    public DeleteCommand(int indexOfTask) {
        this.indexOfTask = indexOfTask;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (indexOfTask > 0 && indexOfTask < (tasks.getSize() - 1)) {
            indexOfTask -= 1;
            Task task = tasks.delete(indexOfTask);
            storage.saveFile(tasks.getTasks());
            Ui.printOutput("Noted. I've removed this task:" + "\n " + task.toString() + "\nNow you have "
                            + tasks.getSize() + " task(s) in the list.");
        }
        else
        {
            throw new DukeException(DukeException.TASK_DOES_NOT_EXIST());
        }
    }
}