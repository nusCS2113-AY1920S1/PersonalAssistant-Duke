package owlmoney.logic.command;

import owlmoney.storage.Storage;
import owlmoney.model.task.TaskList;
import owlmoney.ui.Ui;

/**
 * Class representing a command to exit owlmoney.
 */
public class ExitCommand extends Command {
    /**
     * Creates a new ExitCommand. In particular, sets the exit variable of its superclass
     * to true so the application can exit the next time it checks the exit status.
     */
    public ExitCommand() {
        exit = true;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param tasks The task list.
     * @param ui The user interface displaying events on the task list.
     * @param storage The place where tasks will be stored.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.printMessage("Bye. Hope to see you again soon!");
    }
}
