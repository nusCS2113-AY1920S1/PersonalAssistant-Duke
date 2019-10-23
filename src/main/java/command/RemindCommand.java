package command;

import exception.DukeException;
import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

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
    public void execute(TaskList tasks, Storage storage) throws DukeException {
        boolean outOfBound = indexOfTask < 0 || indexOfTask > (tasks.getSize() - 1);
        if (outOfBound) {
            throw new DukeException(DukeException.taskDoesNotExist());
        }

        Task task = tasks.getTasks().get(indexOfTask);
        task.setReminder(days);
        storage.saveFile(tasks.getTasks());

        Ui.printOutput(String.format("Okay! You'll get a reminder for this task %d days beforehand:", days)
            + "  " + task.toString());
    }
}
