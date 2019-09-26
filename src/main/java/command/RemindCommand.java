package command;

import exception.DukeException;
import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

public class RemindCommand extends Command {
    private int indexOfTask;
    private int days;

    public RemindCommand(int index, int days){
        this.indexOfTask = index;
        this.days = days;
    }

    public void execute(TaskList tasks, Storage storage) throws DukeException{
        boolean outOfBound = indexOfTask < 0 || indexOfTask > (tasks.getSize() - 1);
        if (outOfBound) {
            throw new DukeException(DukeException.TASK_DOES_NOT_EXIST());
        }

        Task task = tasks.getTasks().get(indexOfTask);
        task.setReminder(days);
        storage.saveFile(tasks.getTasks());

        Ui.printMessage(String.format("Okay! You'll get a reminder for this task %d days beforehand:", days));
        Ui.printMessage("  " + task.toString());
    }
}
