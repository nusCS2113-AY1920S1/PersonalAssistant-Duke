package duke.command;

import java.util.ArrayList;

import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;
import duke.task.Task;

/**
 * Class representing a command to find items in the task list matching some keyword,
 * and to display those items.
 * The keyword is case insensitive.
 */
public class FindCommand extends Command {

    private final String keyword;

    /**
     * Creates a new FindCommand with the specified keyword.
     *
     * @param keyword The keyword that is being searched for.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword.trim();
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param tasks The task list.
     * @param ui The user interface displaying events on the task list.
     * @param storage The place where tasks will be stored.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (!keyword.isEmpty() && !keyword.isBlank()) {
            ArrayList<Task> result = tasks.filter(keyword);
            if (result.size() == 0) {
                ui.printError("There are no tasks that match this keyword");
            } else {
                ui.printMessage("Here are the matching tasks in your list:");
                for (int i = 0; i < result.size(); i++) {
                    ui.printMessage(i + 1 + "." + result.get(i).toString());
                }
            }
        } else {
            ui.printError("Keyword cannot be empty");
        }
    }
}
