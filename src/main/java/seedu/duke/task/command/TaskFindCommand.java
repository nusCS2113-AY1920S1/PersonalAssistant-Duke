package seedu.duke.task.command;

import seedu.duke.Duke;
import seedu.duke.common.command.Command;
import seedu.duke.task.TaskList;

/**
 * FindCommand is a specific kind of command used to find a task from task list with a keyword.
 */
public class TaskFindCommand extends Command {
    private String keyword;

    /**
     * Instantiation of find command with all the necessary variables it needs to execute.
     *
     * @param keyword the keyword that the target task needs to match
     */
    TaskFindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the find command by calling the findKeyword function from the task list. Find result will be
     * shown by the UI.
     *
     * @return true after the look after is completed. Note that no result found will be reflected in the UI
     *         output instead of the returned value of this function.
     */
    @Override
    public boolean execute() {
        TaskList taskList = Duke.getModel().getTaskList();
        if (!silent) {
            responseMsg = taskList.findKeyword(keyword);
            Duke.getUI().showResponse(responseMsg);
        }
        return true;
    }
}
