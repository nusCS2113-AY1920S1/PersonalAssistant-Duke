package seedu.duke.task.command;

import seedu.duke.Duke;
import seedu.duke.CommandParser;
import seedu.duke.task.TaskList;
import seedu.duke.common.command.Command;
import seedu.duke.task.entity.Task;

/**
 * DeleteCommand that is used delete a task from the task list with its index.
 */
public class TaskDeleteCommand extends Command {
    private int index;

    /**
     * Instantiation of delete command with all the necessary variables.
     *
     * @param index    the index of task that is to be deleted
     */
    public TaskDeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the delete command by calling the delete function of task list.
     *
     * @return a flag whether deletion is done successfully. Returns false if the delete function of task list
     *         throws an exception.
     */
    @Override
    public boolean execute() {
        try {
            TaskList taskList = Duke.getModel().getTaskList();
            String msg = taskList.delete(index);
            if (!silent) {
                responseMsg = msg;
                Duke.getUI().showResponse(msg);
            }
            return true;
        } catch (CommandParser.UserInputException e) {
            if (!silent) {
                Duke.getUI().showError(e.getMessage());
            }
            return false;
        }
    }
}
