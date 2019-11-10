package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.task.Task;
import chronologer.storage.Storage;
import chronologer.task.TaskList;
import chronologer.ui.UiMessageHandler;

import java.util.ArrayList;

/**
 * Finds all tasks that contain a particular keyword.
 *
 * @author Sai Ganesh Suresh
 * @version v1.3
 */
public class FindCommand extends Command {

    private String keyWord;
    private static final String EMPTY_LIST = "There are no matching tasks in your list.";
    private static final String PRESENT_LIST = "Here are the matching task(s) in your list:";

    public FindCommand(String keyWord) {
        this.keyWord = keyWord;
    }

    /**
     * Finds all the tasks with a particular keyword and passes it to UI which prints to user.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     */
    public void execute(TaskList tasks, Storage storage) throws ChronologerException {
        ArrayList<Task> holdFoundTasks = tasks.find(keyWord);
        if (holdFoundTasks.isEmpty()) {
            UiMessageHandler.outputMessage(EMPTY_LIST);
        } else {
            outputRequiredList(holdFoundTasks,PRESENT_LIST);
        }
    }
}