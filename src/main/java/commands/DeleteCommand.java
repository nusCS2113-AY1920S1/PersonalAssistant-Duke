package commands;
import tasks.Task;
import tasks.TaskList;
import ui.Ui;
import storage.Storage;
import java.util.ArrayList;

/**
 * DeleteCommand is a public class that inherits from abstract class Command
 * A DeleteCommand object encapsulates the index of task that is to be deleted.
 * @author Ivan Andika Lie
 */
public class DeleteCommand extends Command {
    private int index;

    /**
     * This is a constructor DeleteCommand
     * @param index the index of task to be deleted
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * The object will execute the "delete" command, updating the current tasks, ui, and storage in the process
     * @param tasks the TaskList object in which the the indexed task is supposed to be deleted from
     * @param ui the ui object to display the user interface of a "delete" command
     * @param storage the storage object that stores the list of tasks
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> currentTasks = tasks.getTasks();
        Task currentTask = currentTasks.get(index - 1);
        tasks.delete(index);
        ui.showDeleted(currentTask, currentTasks);
        storage.updateFile(currentTasks);
    }
}
