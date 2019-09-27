package duke.commands;
import duke.tasks.Task;
import duke.tasks.TaskList;
import duke.ui.Ui;
import duke.storage.Storage;
import java.util.ArrayList;

/**
 * ListCommand is a public class that inherits from abstract class Command
 * @author Ivan Andika Lie
 */
public class ListCommand extends Command {
    /**
     * The object will execute the "list" command
     * @param tasks the TaskList object in which the task(s) is supposed to be listed
     * @param ui the ui object to display the user interface of an "list" command
     * @param storage the storage object that stores the list of tasks
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> currentTasks = tasks.getTasks();
        ui.showList(currentTasks);
    }
}
