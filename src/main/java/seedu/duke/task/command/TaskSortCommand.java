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

    public TaskSortCommand(TaskList.SortBy sortBy) {
        this.sortType = sortBy;
    }

    @Override
    public boolean execute(Model model) {
        TaskList taskList = model.getTaskList();
        if (!silent) {
            responseMsg = taskList.setSortType(sortType);
            UI.getInstance().showResponse(responseMsg);
        }
        return true;
    }

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
