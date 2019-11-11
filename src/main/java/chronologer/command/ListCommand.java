package chronologer.command;

import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.ui.UiMessageHandler;

import java.util.ArrayList;

/**
 * Prints the entire list of tasks.
 *
 * @author Sai Ganesh Suresh
 * @version v1.4
 */
public class ListCommand extends Command {

    private static final String EMPTY_LIST = "You have currently no tasks in your list.";
    private static final String PRESENT_LIST = "Here are the task(s) in your list:";

    /**
     * Outputs the entire list of tasks to the UI which will print it.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     * @param history Allows the history features to be done.
     */
    public void execute(TaskList tasks, Storage storage, ChronologerStateList history) {
        ArrayList<Task> currentList = tasks.getTasks();
        if (tasks.getSize() == 0) {
            UiMessageHandler.outputMessage(EMPTY_LIST);
        } else {
            outputRequiredList(currentList, PRESENT_LIST);
        }
    }
}
