package command;

import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

import java.util.ArrayList;

/**
 * Prints the entire list of tasks.
 *
 * @author Sai Ganesh Suresh
 * @version v1.0
 */
public class ListCommand extends Command {
    /**
     * Outputs the entire list of tasks to the UI which will print it.
     *
     * @param tasks   this string holds command type determinant to decide how to
     *                process the user input.
     * @param storage this parameter provides the execute function the storage to
     *                allow the saving of the file.
     */
    public void execute(TaskList tasks, Storage storage) {
        ArrayList<Task> currentList = tasks.getTasks();
        if (tasks.getSize() == 0) {
            Ui.printOutput("You have currently no tasks in your list.");
        } else {
            Ui.printDash();
            Ui.printOutput("Here are the task(s) in your list:");
            outputRequiredList(currentList);
        }
    }
}
