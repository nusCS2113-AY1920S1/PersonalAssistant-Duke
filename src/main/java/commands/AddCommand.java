package commands;
import java.util.ArrayList;
import tasks.Task;
import tasks.TaskList;
import ui.Ui;
import storage.Storage;

/**
 * AddCommand is a public class that inherits from abstract class Command
 * An AddCommand object encapsulates the current task that is to be added.
 * @author Ivan Andika Lie
 */
public class AddCommand extends Command {
    private Task task;

    /**
     * This is a constructor for AddCommand which create a new AddCommand object with
     * the task specified as the instance field task.
     * @param task The task to be added.
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    /**
     * The object will execute the "add" command, updating the current tasks, ui, and storage in the process.
     * @param tasks the TaskList object in which the task is supposed to be added
     * @param ui the ui object to display the user interface of an "add" command
     * @param storage the storage object that stores the list of tasks
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> currentTasks = tasks.getTasks();
        currentTasks.add(task);
        ui.showAdded(task, currentTasks);
        storage.updateFile(currentTasks);
    }

}
