package seedu.duke.task.command;

import seedu.duke.CommandParser;
import seedu.duke.Duke;
import seedu.duke.task.TaskList;
import seedu.duke.common.command.Command;

/**
 * Add a task which do after another task.
 */
public class TaskDoAfterCommand extends Command {

    private TaskList taskList;
    private int index;
    private String doAfterDescription;

    /**
     * Instantiation of do after command.
     *
     * @param taskList           list of tasks.
     * @param index              index of task.
     * @param doAfterDescription of task.
     */
    public TaskDoAfterCommand(TaskList taskList, int index, String doAfterDescription) {
        this.taskList = taskList;
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
