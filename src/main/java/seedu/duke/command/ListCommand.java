package seedu.duke.command;

import seedu.duke.Duke;
import seedu.duke.TaskList;

/**
 * ListCommand is a specific kind of command used to display all task in a task list.
 */
public class ListCommand extends Command {
    private TaskList taskList;

    /**
     * Instantiation of the list command with the target task list.
     *
     * @param taskList the target task list to be displayed.
     */
    public ListCommand(TaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * Executes the list command by calling the UI to display the target task list.
     *
     * @return true after display is completed.
     */
    @Override
    public boolean execute() {
        if (!silent) {
            String msg = this.taskList.toString();
            responseMsg = msg;
            Duke.getUI().showResponse(msg);
        }
        return true;
    }
}
