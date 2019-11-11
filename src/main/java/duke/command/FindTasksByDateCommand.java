package duke.command;

import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;


//@@author Dou-Maokang
/**
 * Represents a command to find tasks with a target date.
 */
public class FindTasksByDateCommand extends Command {
    protected String targetDate;

    /**
     * Creates a command with the target date.
     *
     * @param targetDate The target date to be searched.
     */
    public FindTasksByDateCommand(String targetDate) {
        this.targetDate = targetDate;
    }

    /**
     * Executes a command that locates matching tasks in task list and outputs the result (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user the matching tasks based on the keyword.
     * @return List of tasks.
     */
    @Override
    public String executeGui(TaskList items, Ui ui) {
        String str = ui.showFindTasksByDateGui(items, targetDate);
        return str;
    }

    /**
     * Executes a command that overwrites existing storage with the updated task list.
     * (Not in use)
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is executed successfully.
     * @param storage The storage to be overwritten.
     */
    @Override
    public void executeStorage(TaskList items, Ui ui, Storage storage) {

    }
}
//@@author



