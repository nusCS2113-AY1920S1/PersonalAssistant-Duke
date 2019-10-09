package command;

/**
 * The FindCommand class is used when the user intends to check all tasks for a keyword.
 *
 * @author Sai Ganesh Suresh
 * @version v1.0
 */

import exception.DukeException;
import task.Task;
import storage.Storage;
import task.TaskList;
import ui.Ui;

import java.util.ArrayList;

public class FindCommand extends Command {

    private String keyWord;

    public FindCommand(String keyWord) {
        this.keyWord = keyWord;
    }

    /**
     * This execute function is used to find the tasks with a particular keyword.
     *
     * @param tasks   this string holds command type determinant to decide how to
     *                process the user input.
     * @param storage this parameter provides the execute function the storage to
     *                allow the saving of the file.
     */

    public void execute(TaskList tasks, Storage storage) throws DukeException {
        ArrayList<Task> holdFoundTasks = tasks.find(keyWord);
        if (holdFoundTasks.isEmpty()) {
            Ui.printOutput("There are no matching tasks in your list.");
        } else {
            Ui.printOutput("Here are the matching task(s) in your list:");
            Integer i = 1;
            Integer j = 1;
            Ui.printDash();
            for (Task task : holdFoundTasks) {
                Ui.printMessage(i++ + "." + task.toString());
                Ui.userOutputForUI += j++ + "." + task.toString() + "\n";
            }
            Ui.printDash();
        }
    }
}