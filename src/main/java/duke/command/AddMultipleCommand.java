package duke.command;

import duke.storage.Storage;
import duke.task.PriorityList;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

import java.util.ArrayList;

/**
 * Representing a command that adds multiple tasks.
 */
//@@author e0318465
public class AddMultipleCommand extends Command {
    protected ArrayList<Task> tasks;

    /**
     * Creates a command with the specified task list.
     *
     * @param tasks The array list of tasks.
     */
    public AddMultipleCommand(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Executes a command that adds the tasks into task list and outputs the result.
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that they are added successfully.
     */
    @Override
    public void execute(TaskList items, Ui ui) {
        for (Task curTask : tasks) {
            items.add(curTask);
        }
        ui.showAdd(items);
    }

    /**
     * Executes a command that adds the tasks and priority into task list and outputs the result.
     *
     * @param items The task list that contains a list of tasks.
     * @param priorities Priority level of task.
     * @param ui To tell the user that it is executed successfully.
     */
    public void execute(TaskList items, PriorityList priorities, Ui ui) {
        for (Task curTask : tasks) {
            items.add(curTask);
        }
        ui.showAdd(items);
        priorities.addMultiDefaultPriority(tasks.size());
    }

    /**
     * Executes a command that adds the tasks into task list and outputs the result (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that they are added successfully.
     * @return String to be outputted to the user.
     */
    @Override
    public String executeGui(TaskList items, Ui ui) {
        for (Task curTask : tasks) {
            items.add(curTask);
        }

        String str = Ui.showAddGui(items);
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