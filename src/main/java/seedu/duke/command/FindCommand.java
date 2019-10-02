package seedu.duke.command;

import seedu.duke.Duke;
import seedu.duke.TaskList;

/**
 * FindCommand is a specific kind of command used to find a task from task list with a keyword.
 */
public class FindCommand extends Command {
    private TaskList taskList;
    private String keyword;

    /**
     * Instantiation of find command with all the necessary variables it needs to execute.
     *
     * @param taskList the task list where the task is looked for
     * @param keyword  the keyword that the target task needs to match
     */
    public FindCommand(TaskList taskList, String keyword) {
        this.taskList = taskList;
        this.keyword = keyword;
    }

    /**
     * Executes the find command by calling the findKeyword function from the task list. Find result will be
     * shown by the UI.
     *
     * @return true after the look after is completed. Note that no result found will be reflected in the UI
     *      output instead of the returned value of this function.
     */
    @Override
    public boolean execute() {
        String msg = this.taskList.findKeyword(keyword);
        if (!silent) {
            responseMsg = msg;
            Duke.getUI().showResponse(msg);
        }
        return true;
    }
}
