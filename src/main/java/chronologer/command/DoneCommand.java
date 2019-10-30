package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.task.Task;
import chronologer.storage.Storage;
import chronologer.task.TaskList;
import chronologer.ui.UiTemporary;

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
    public void execute(TaskList tasks, Storage storage) throws ChronologerException {
        if (isIndexValid(indexOfTask, tasks.getSize())) {
            Task task = tasks.markAsDone(indexOfTask);
            ChronologerStateList.addState((tasks.getTasks()));
            storage.saveFile(tasks.getTasks());
            UiTemporary.printOutput("Nice! I've marked this task as done: " + task.toString());
        }
    }
}
