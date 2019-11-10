package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.task.Task;
import chronologer.storage.Storage;
import chronologer.task.TaskList;
import chronologer.ui.UiMessageHandler;

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
    public void execute(TaskList tasks, Storage storage) throws ChronologerException {
        if (isIndexValid(indexOfTask, tasks.getSize())) {
            Task task = tasks.delete(indexOfTask);
            ChronologerStateList.addState(tasks.getTasks());
            storage.saveFile(tasks.getTasks());
            UiMessageHandler.outputMessage("Noted. I've removed this task:" + "\n " + task.toString()
                + "\nNow you have " + tasks.getSize() + " task(s) in the list.");
        }
    }
}