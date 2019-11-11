package duke.command;

import duke.storage.Storage;
import duke.task.PriorityList;
import duke.task.TaskList;
import duke.ui.Ui;


//@@author Dou-Maokang
/**
 * Represents a command to find tasks with a target priority.
 */
public class FindTasksByPriorityCommand extends Command {
    protected int targetPriority;

    /**
     * Creates a command with the target priority.
     *
     * @param targetPriority The target priority to be searched.
     */
    public FindTasksByPriorityCommand(int targetPriority) {
        this.targetPriority = targetPriority;
    }

    /**
     * Executes a command that locates matching tasks in task list and outputs the result (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param priorityList The list of priorities associated with the task list.
     * @param ui To tell the user the matching tasks based on the keyword.
     * @return List of matching tasks.
     */
    @Override
    public String executeGui(TaskList items, PriorityList priorityList, Ui ui) {
        String str = ui.showFindTasksByPriorityGui(items, priorityList, targetPriority);
        return str;
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
        return "";
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