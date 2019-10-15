package seedu.duke.task.command;

import seedu.duke.CommandParser;
import seedu.duke.Duke;
import seedu.duke.task.TaskList;
import seedu.duke.common.command.Command;

/**
 * Adds a priority level for a task.
 */
public class TaskSetPriorityCommand extends Command {

    private TaskList taskList;
    private int index;
    private String priorityLevel;

    /**
     * Instantiation of set priority command.
     *
     * @param taskList      list of tasks
     * @param index         index of task
     * @param priorityLevel priority level set for the task
     */
    public TaskSetPriorityCommand(TaskList taskList, int index, String priorityLevel) {
        this.taskList = taskList;
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
        String msg = "";
        try {
            msg = taskList.setPriority(index, priorityLevel);
        } catch (CommandParser.UserInputException e) {
            Duke.getUI().showError(e.getMessage());
            return false;
        }
        Duke.getUI().showResponse(msg);
        return true;
    }
}