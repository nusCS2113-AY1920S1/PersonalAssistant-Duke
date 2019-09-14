package command;

import exception.DukeException;
import task.Task;
import storage.Storage;
import task.TaskList;
import ui.Ui;
import java.util.ArrayList;

/**
 * The FindCommand class is used when the user intends to check all tasks for a keyword.
 *
 * @author Sai Ganesh Suresh
 * @version v1.0
 */
public class FindCommand extends Command{

    private String keyWord;

    public FindCommand(String keyWord) {
        this.keyWord = keyWord;
    }

    /**
     * This execute function is used to add the respective tasks to the TaskList and save to persistent storage.
     *
     * @param tasks this string holds command type determinant to decide how to process the user input.
     * @param ui this string holds the description of the task provided by the user.
     * @param storage this parameter provides the execute function the storage to allow the saving of the file.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        ArrayList<Task> holdFoundTasks = tasks.find(keyWord);
        if (holdFoundTasks.isEmpty()) {
            Ui.printMessage("There are no matching tasks in your list.");
        }
        else{
            Ui.printMessage("Here are the matching task(s) in your list:");
        }

        int i = 1;
        for (Task task : holdFoundTasks) {
            Ui.printMessage(i++ + "." + task.toString());
        }
    }
}