package command;

import task.TaskList;
import ui.Ui;
import storage.Storage;

import java.util.ArrayList;

/**
 * Represents a specified command as FindCommand by extending the <code>Command</code> class.
 * Finds all tasks relevant with the searched keyword.
 * Responses with the result.
 */
public class FindCommand extends Command {
    private String target;

    /**
     * Constructs a <code>FindCommand</code> object
     * with given searched keyword.
     *
     * @param target The searched keyword.
     */
    public FindCommand(String target) {
        super("find");
        this.target = target;
    }

    /**
     * Finds relevant tasks from taskList of Duke with the given keyword and
     * responses the result to user by using ui of Duke.
     *
     * @param tasks The taskList of Duke.
     * @param ui The ui of Duke.
     * @param storage The storage of Duke.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Integer> matchedList = tasks.find(target);
        if (matchedList.size() == 0) {
            ui.println("No results found.");
            return;
        }
        ui.println("Here are the matching tasks in your list:");
        int count = 1;
        for (int i : matchedList) {
            ui.println(count + "." + tasks.getTaskInfo(i));
            count ++;
        }
    }
}
