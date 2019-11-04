package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.ui.UiTemporary;

/**
 * Adds a reminder.
 *
 * @author Fauzan
 * @version v1.3
 */
public class RemindCommand extends Command {
    private Integer indexOfTask;
    private Integer days;

    public RemindCommand(Integer indexOfTask, Integer days) {
        this.indexOfTask = indexOfTask;
        this.days = days;
    }

    /**
     * Creates a reminder for a task and saves the updated TaskList to persistent storage.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) throws ChronologerException {
        if (!isIndexValid(indexOfTask,tasks.getSize())) {
            UiTemporary.printOutput(ChronologerException.taskDoesNotExist());
            throw new ChronologerException(ChronologerException.taskDoesNotExist());
        }

        Task task = tasks.getTasks().get(indexOfTask);
        task.setReminder(days);
        ChronologerStateList.addState((tasks.getTasks()));
        storage.saveFile(tasks.getTasks());

        UiTemporary.printOutput(String.format("Okay! You'll get a reminder for this task %d days beforehand:", days)
            + "  " + task.toString());
    }
}
