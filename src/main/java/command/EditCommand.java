package command;

import exception.DukeException;
import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;


/**
 * The EditCommand class is used whenever the user wishes to edit their task description
 * due to any changes.
 */
public class EditCommand extends Command {

    private int indexOfTask;
    private String newDescription;

    public EditCommand(int indexOfTask, String newDescription) {
        this.indexOfTask = indexOfTask;
        this.newDescription = newDescription;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws DukeException {
        if (indexOfTask >= 0 && indexOfTask <= (tasks.getSize() - 1)) {
            Task taskToEdit = tasks.editTaskDescription(indexOfTask, newDescription);
            storage.saveFile(tasks.getTasks());
            Ui.printOutput("Noted. Your new task description is:" + "\n " + taskToEdit.description);
        } else {
            throw new DukeException(DukeException.taskDoesNotExist());
        }
    }
}
