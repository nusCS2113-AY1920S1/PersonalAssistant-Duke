package command;

import exception.DukeException;
import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

public class IgnoreCommand extends Command {
    private int indexOfTask;

    public IgnoreCommand(int index) {
        indexOfTask = index;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws DukeException {

        if (indexOfTask < 0 || indexOfTask > (tasks.getSize() - 1)) {
            throw new DukeException(DukeException.taskDoesNotExist());
        }

        Task task = tasks.markAsIgnorable(indexOfTask);
        storage.saveFile(tasks.getTasks());
        Ui.printOutput("Noted. This task has been marked as ignored:\n" + task.toString());
    }
}
