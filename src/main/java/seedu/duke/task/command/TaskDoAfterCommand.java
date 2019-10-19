package seedu.duke.task.command;

import seedu.duke.CommandParser;
import seedu.duke.Duke;
import seedu.duke.task.TaskList;
import seedu.duke.common.command.Command;
import seedu.duke.task.entity.Task;

/**
 * Add a task which do after another task.
 */
public class TaskDoAfterCommand extends Command {

    private int index;
    private String doAfterDescription;

    /**
     * Instantiation of do after command.
     *
     * @param index              index of task.
     * @param doAfterDescription of task.
     */
    public TaskDoAfterCommand(int index, String doAfterDescription) {
        this.index = index;
        this.doAfterDescription = doAfterDescription;
    }

    /**
     * Set do after task.
     *
     * @return true.
     */
    @Override
    public boolean execute() {
        TaskList taskList = Duke.getModel().getTaskList();
        String msg = "";
        try {
            msg = taskList.setDoAfter(index, doAfterDescription);
        } catch (CommandParser.UserInputException e) {
            Duke.getUI().showError(e.getMessage());
            return false;
        }
        Duke.getUI().showResponse(msg);
        return true;
    }
}
