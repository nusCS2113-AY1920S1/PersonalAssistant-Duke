package seedu.duke.task.command;

import seedu.duke.common.command.Command;
import seedu.duke.common.model.Model;
import seedu.duke.task.TaskList;
import seedu.duke.ui.UI;

public class TaskClearListCommand extends Command {

    /**
     * Instantiation of the clear list command.
     */
    public TaskClearListCommand() {

    }

    /**
     * Executes the clear command by calling the clear list function of task list.
     *
     * @return a flag whether clearing of task list is done successfully. Returns false if the delete
     *     function of task list throws an exception
     */
    @Override
    public boolean execute(Model model) {
        TaskList taskList = model.getTaskList();
        String msg = taskList.clearList();
        if (!silent) {
            responseMsg = msg;
            UI.getInstance().showResponse(responseMsg);
        }
        return true;
    }
}
