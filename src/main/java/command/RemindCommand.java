package command;

import exception.DukeException;
import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

public class RemindCommand extends Command {
    private int indexOfTask;
    private int days;

    public RemindCommand(int index, int days) {
        this.indexOfTask = index;
        this.days = days;
    }

    /**
     * This execute function is used to add the respective tasks to the TaskList and
     * save to persistent storage.
     *
     * @param tasks   this string holds command type determinant to decide how to
     *                process the user input.
     * @param storage this parameter provides the execute function the storage to
     *                allow the saving of the file.
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
