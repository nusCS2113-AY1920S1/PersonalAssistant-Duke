package command;

import exception.DukeException;
import task.Task;
import storage.Storage;
import task.TaskList;
import ui.Ui;

import java.util.ArrayList;

/**
 * Finds all tasks that contain a particular keyword.
 *
 * @author Sai Ganesh Suresh
 * @version v1.3
 */
public class FindCommand extends Command {

    private String keyWord;

    public FindCommand(String keyWord) {
        this.keyWord = keyWord;
    }

    /**
     * Finds all the tasks with a particular keyword and passes it to UI which prints to user.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     */
    public void execute(TaskList tasks, Storage storage) throws DukeException {
        ArrayList<Task> holdFoundTasks = tasks.find(keyWord);
        if (holdFoundTasks.isEmpty()) {
            Ui.printOutput("There are no matching tasks in your list.");
        } else {
            Ui.printOutput("Here are the matching task(s) in your list:");
            outputRequiredList(holdFoundTasks);
        }
    }
}