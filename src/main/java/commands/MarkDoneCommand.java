package commands;
import tasks.Task;
import tasks.TaskList;
import ui.Ui;
import storage.Storage;
import java.util.ArrayList;

/**
 * MarkDoneCommand is a public class that inherits form abstract class Command
 * A MarkDoneCommand object encapsulates the index of task to be marked as done
 * @author Ivan Andika Lie
 */
public class MarkDoneCommand extends Command{
    private int index;

    /**
     * This is a constructor for MarkDoneCommand
     * @param index the index of task to be marked done
     */
    public MarkDoneCommand(int index) {
        this.index = index;
    }

    /**
     * The object will execute the "mark done" command, updating the current tasks, ui, and storage in the process
     * @param tasks the TaskList object to be marked done
     * @param ui the ui object to display the user interface of an "mark done" command
     * @param storage the storage object that stores the list of tasks
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> currentTasks = tasks.getTasks();
        Task currentTask = currentTasks.get(index - 1);
        currentTask.markAsDone();
        storage.updateFile(currentTasks);
        ui.showDone(currentTask);
    }
}
