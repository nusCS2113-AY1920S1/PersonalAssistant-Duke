package duke.command;

import duke.storage.Storage;
import duke.task.PriorityList;
import duke.task.TaskList;
import duke.ui.Ui;


//@@author Dou-Maokang
/**
 * Representing a command to set priorities for every task.
 */
public class SetPriorityCommand extends Command {
    protected int taskNum;
    protected int priority;

    /**
     * Representing a command that set priority for each task.
     *
     * @param taskNum The number of the task in the task list.
     * @param priority The priority level we want to set.
     */
    public SetPriorityCommand(int taskNum, int priority) {
        this.taskNum = taskNum;
        this.priority = priority;
    }

    /**
     * Executes a command that adds the task into task list and outputs the result.
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is added successfully.
     */
    public void execute(TaskList items, Ui ui) {
    }

    /**
     * Executes a command that adds the priority into priority list and outputs the result.
     *
     * @param items The task list that contains a list of tasks.
     * @param priorityList List of priorities.
     * @param ui To tell the user that it is executed successfully.
     */
    public void execute(TaskList items, PriorityList priorityList, Ui ui) {
        priorityList.setPriority(taskNum, priority);
        ui.showSetPriority(items, taskNum, priority);
    }

    /**
     * Executes a command that adds the task into task list and outputs the result (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is added successfully.
     * @return String to be outputted to the user.
     */
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
    public void executeStorage(TaskList items, Ui ui, Storage storage) {
    }
}
//@@author