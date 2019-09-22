package owlmoney.logic.command;

import owlmoney.storage.Storage;
import owlmoney.model.task.TaskList;
import owlmoney.ui.Ui;

/**
 * Class representing a command to list items in a task list.
 */
public class ListCommand extends Command {
    /**
     * Executes this command on the given task list and user interface.
     *
     * @param tasks The task list.
     * @param ui The user interface displaying events on the task list.
     * @param storage The place where tasks will be stored.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.size() == 0) {
            ui.printError("The list is empty");
        }
        for (int i = 1; i <= tasks.size(); i++) {
            ui.printMessage(i + "." + tasks.get(i).toString());
        }
    }
}
