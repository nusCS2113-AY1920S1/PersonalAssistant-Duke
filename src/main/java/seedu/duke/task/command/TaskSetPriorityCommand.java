package seedu.duke.task.command;

import seedu.duke.CommandParser;
import seedu.duke.Duke;
import seedu.duke.task.TaskList;
import seedu.duke.common.command.Command;

/**
 * Adds a priority level for a task.
 */
public class TaskSetPriorityCommand extends Command {

    private int index;
    private String priorityLevel;

    /**
     * Instantiation of set priority command.
     *
     * @param index         index of task
     * @param priorityLevel priority level set for the task
     */
    TaskSetPriorityCommand(int index, String priorityLevel) {
        this.index = index;
        this.priorityLevel = priorityLevel;
    }

    /**
     * Sets priority level for a task.
     *
     * @return true
     */
    @Override
    public boolean execute() {
        try {
            TaskList taskList = Duke.getModel().getTaskList();
            String msg = taskList.setPriority(index, priorityLevel);
            if (!silent) {
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