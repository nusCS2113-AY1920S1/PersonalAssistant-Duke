package command;

import exception.DukeException;
import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

public class IgnoreCommand extends Command {
    private int indexOfTask;
    private boolean isIgnore;

    public IgnoreCommand(int index, boolean isIgnore) {
        indexOfTask = index;
        this.isIgnore = isIgnore;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws DukeException {

        if (indexOfTask < 0 || indexOfTask > (tasks.getSize() - 1)) {
            throw new DukeException(DukeException.taskDoesNotExist());
        }

        if (isIgnore) {
            Task task = tasks.markAsIgnorable(indexOfTask);
            storage.saveFile(tasks.getTasks());
            Ui.printOutput("Noted. This task has been marked as ignored:\n" + task.toString());
        } else {
            Task task = tasks.markAsUnignorable(indexOfTask);
            storage.saveFile(tasks.getTasks());
            Ui.printOutput("Noted. This task is no longer ignored:\n" + task.toString());
        }
    }
}
