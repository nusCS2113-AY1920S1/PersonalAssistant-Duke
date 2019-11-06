package duke.command;

import duke.enums.Numbers;
import duke.storage.Storage;
import duke.task.FilterList;
import duke.task.TaskList;
import duke.ui.Ui;
import duke.task.Todo;
import duke.task.Deadline;
import duke.task.FixedDuration;
import duke.task.Repeat;

//@@author talesrune
/**
 * Represents a command that filters certain tasks in task list from the type of task.
 */
public class FilterCommand extends Command {
    protected String taskType;

    /**
     * Creates a command with the specified type of task.
     *
     * @param taskType The task's type to be filtered.
     */
    public FilterCommand(String taskType) {
        this.taskType = taskType;
    }

    /**
     * Executes a command that filters tasks in task list and outputs the result.
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user the filtered tasks based on the task's type.
     */
    @Override
    public void execute(TaskList items, Ui ui) {
        ui.showFilter(items, taskType);
    }

    /**
     * Executes a command that filters tasks in task list and updates the updated list.
     *
     * @param items The task list that contains a list of tasks.
     * @param filterList  The task list that contains a list of filtered tasks.
     */
    @Override
    public void execute(TaskList items, FilterList filterList) {
        filterList.clear();
        filterList.setFilterIndex(Numbers.MINUS_ONE.value);
        for (int i = Numbers.ZERO.value; i < items.size(); i++) {
            if (taskType.equals("todo") && items.get(i) instanceof Todo) {
                filterList.add(items.get(i));
                filterList.setFilterIndex(Numbers.ONE.value);
            } else if (taskType.equals("deadline") && items.get(i) instanceof Deadline) {
                filterList.add(items.get(i));
                filterList.setFilterIndex(Numbers.TWO.value);
            } else if (taskType.equals("repeat") && items.get(i) instanceof Repeat) {
                filterList.add(items.get(i));
                filterList.setFilterIndex(Numbers.THREE.value);
            } else if (taskType.equals("fixedduration") && items.get(i) instanceof FixedDuration) {
                filterList.add(items.get(i));
                filterList.setFilterIndex(Numbers.FOUR.value);
            }
        }
    }

    /**
     * Executes a command that locates matching tasks in task list and outputs the result (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user the matching tasks based on the task's type.
     * @return List of tasks.
     */
    @Override
    public String executeGui(TaskList items, Ui ui) {
        String str = ui.showFilterGui(items, taskType);
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