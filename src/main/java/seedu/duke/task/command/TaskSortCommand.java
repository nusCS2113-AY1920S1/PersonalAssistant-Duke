package seedu.duke.task.command;

import seedu.duke.common.command.Command;
import seedu.duke.common.model.Model;
import seedu.duke.task.TaskList;
import seedu.duke.ui.UI;

/**
 * SortCommand is a specific kind of command used to sort the task list according to the input.
 */
public class TaskSortCommand extends Command {

    private TaskList.SortBy sortType;

    /**
     * Instantiation of the SortCommand which sorts the task list.
     */
    public TaskSortCommand(TaskList.SortBy sortBy) {
        this.sortType = sortBy;
    }

    /**
     * Executes sort command to sort the task list.
     *
     * @return true when task list is sorted.
     */
    @Override
    public boolean execute(Model model) {
        TaskList taskList = model.getTaskList();
        if (!silent) {
            responseMsg = taskList.setSortType(sortType);
            UI.getInstance().showResponse(responseMsg);
        }
        return true;
    }

    /**
     * Gets the sort type according to the input.
     *
     * @param input the input sort type to sort the task
     * @return
     */
    public static TaskList.SortBy getSortType(String input) {
        TaskList.SortBy sortType = null;
        input = input.toUpperCase();
        if (sortType.PRIORITY.name().equals(input)) {
            return sortType.PRIORITY;
        } else if (sortType.TIME.name().equals(input)) {
            return sortType.TIME;
        } else if (sortType.STATUS.name().equals(input)) {
            return sortType.STATUS;
        } else {
            return null;
        }
    }
}
