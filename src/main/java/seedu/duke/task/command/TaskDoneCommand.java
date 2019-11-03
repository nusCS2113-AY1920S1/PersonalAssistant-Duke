package seedu.duke.task.command;

import seedu.duke.common.parser.CommandParseHelper;
import seedu.duke.common.command.Command;
import seedu.duke.common.model.Model;
import seedu.duke.task.TaskList;
import seedu.duke.ui.UI;

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
    public TaskDoneCommand(int i) {
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
    public boolean execute(Model model) {
        try {
            TaskList taskList = model.getTaskList();
            String msg = taskList.markDone(index);
            if (!silent) {
                responseMsg = msg;
                UI.getInstance().showResponse(msg);
            }
            return true;
        } catch (CommandParseHelper.CommandParseException e) {
            if (!silent) {
                UI.getInstance().showError(e.getMessage());
            }
            return false;
        }
    }
}
