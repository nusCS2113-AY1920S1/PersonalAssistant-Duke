package commands;
import tasks.Task;
import tasks.TaskList;
import ui.Ui;
import storage.Storage;
import java.util.ArrayList;

/**
 * The FindCommand is a public class that extends from the abstract class Command
 * It encapsulates the String to find in the current TaskList
 */
public class FindCommand extends Command {
    private String description;

    /**
     * FindCommand is a constructor that store the string to find
     * @param description the description of the string to find
     */
    public FindCommand(String description) {
        this.description = description;
    }

    /**
     * This function will execute the "find" command
     * @param tasks the TaskList object in which the task is supposed to be found
     * @param ui the ui object to display the user interface of an "find" command
     * @param storage the storage object that stores the list of tasks
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        ArrayList<Task> currentTasks = tasks.getTasks();
        for (Task element: currentTasks) {
            String currentTaskString = element.toString();
            if (currentTaskString.contains(description)) {
                matchingTasks.add(element);
            }
        }
        ui.showList(matchingTasks);
    }
}
