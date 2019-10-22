package seedu.duke.task.command;

import seedu.duke.CommandParseHelper;
import seedu.duke.Duke;
import seedu.duke.common.command.Command;
import seedu.duke.task.TaskList;

import java.util.ArrayList;

/**
 * DoneCommand is a specific kind of command used to mark a task as done.
 */
public class TaskDoneCommand extends Command {
    private int index;

    /**
     * Instantiation of done command with the index of the target task.
     *
     * @param i the index of the target task to be marked as done
     */
    TaskDoneCommand(int i) {
        index = i;
    }

    public TaskDoneCommand(int i, ArrayList<Option> inputOptionList) {
        index = i;
        optionList = inputOptionList;
    }

    /**
     * Executes the done command by calling the markDone function of the task list.
     *
     * @return a flag whether the task is successfully marked as done. Returns false if the markDone function
     *         throws exception.
     */
    @Override
    public boolean execute() {
        try {
            TaskList taskList = Duke.getModel().getTaskList();
            String msg = taskList.markDone(index);
            if (!silent) {
                responseMsg = msg;
                Duke.getUI().showResponse(msg);
            }
            return true;
        } catch (CommandParseHelper.UserInputException e) {
            if (!silent) {
                Duke.getUI().showError(e.getMessage());
            }
            return false;
        }
    }
}
