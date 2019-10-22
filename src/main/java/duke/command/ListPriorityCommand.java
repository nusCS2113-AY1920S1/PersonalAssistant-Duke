package duke.command;

import duke.storage.Storage;
import duke.task.PriorityList;
import duke.ui.Ui;
import duke.task.TaskList;

//@@author Dou-Maokang
/**
 * Representing a command that lists all tasks stored in task list.
 */
public class ListPriorityCommand extends Command {

    /**
     * Executes a command with task list and ui.
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is executed successfully.
     */
    public void execute(TaskList items, Ui ui) {
    }


    /**
     * Executes a command that gathers all tasks from task list and outputs the result.
     *
     * @param items The task list that contains a list of tasks.
     * @param priorities The list of priorities.
     * @param ui To tell the user the list of tasks stored in task list.
     */

    public void execute(TaskList items, PriorityList priorities, Ui ui) {
        ui.showTaskListWithPriority(items, priorities);
    }

    /**
     * Executes a command that gathers all tasks from task list and outputs the result (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user the list of tasks stored in task list.
     * @return String to be outputted to the user.
     */

    public String executeGui(TaskList items, Ui ui) {
        String str = Ui.showTaskListGui(items);
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

    public void executeStorage(TaskList items, Ui ui, Storage storage) {
    }
}
//@@author