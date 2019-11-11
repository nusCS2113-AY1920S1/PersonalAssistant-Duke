package duke.command;

import duke.storage.Storage;
import duke.task.PriorityList;
import duke.ui.Ui;
import duke.task.TaskList;

//@@author Dou-Maokang
/**
 * Represents a command that lists all tasks stored in task list.
 */
public class ListPriorityCommand extends Command {

    /**
     * Executes a command that gathers all tasks from task list and outputs the result (GUI).
     *
     * @param items      The task list that contains a list of tasks.
     * @param priorities The list of priorities.
     * @param ui         To tell the user the list of tasks stored in task list.
     * @return String to be outputted to the user.
     */
    public String executeGui(TaskList items, PriorityList priorities, Ui ui) {
        checkSize(items, priorities);
        String str = ui.showTaskListWithPriorityGui(items, priorities);
        return str;
    }


    /**
     * Executes a command that gathers all tasks from task list and outputs the result (GUI).
     * (Not in use)
     *
     * @param items The task list that contains a list of tasks.
     * @param ui    To tell the user the list of tasks stored in task list.
     * @return String to be outputted to the user.
     */
    public String executeGui(TaskList items, Ui ui) {
        return null;
    }

    /**
     * Executes a command that overwrites existing storage with the updated task list.
     * (Not in use)
     *
     * @param items   The task list that contains a list of tasks.
     * @param ui      To tell the user that it is executed successfully.
     * @param storage The storage to be overwritten.
     */

    public void executeStorage(TaskList items, Ui ui, Storage storage) {
    }


    /**
     * Add or delete values from priority list if there's a mismatch between the sizes of priority list and items.
     *
     * @param items      The task list.
     * @param priorities The priority list.
     */
    public void checkSize(TaskList items, PriorityList priorities) {
        int prioritySize = priorities.getSize();
        int taskSize = items.size();
        if (prioritySize < taskSize) {
            for (int i = 0; i < taskSize - prioritySize; i++) {
                priorities.addDefaultPriority();
            }
        } else if (prioritySize > taskSize) {
            for (int i = 0; i < prioritySize - taskSize; i++) {
                priorities.remove(priorities.getSize() - 1);
            }
        }
    }
}
//@@author