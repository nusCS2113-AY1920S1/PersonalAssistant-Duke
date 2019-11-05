package seedu.duke.task.command;

import seedu.duke.common.parser.CommandParseHelper;
import seedu.duke.common.command.Command;
import seedu.duke.common.model.Model;
import seedu.duke.task.TaskList;
import seedu.duke.task.entity.Task;
import seedu.duke.ui.UI;

/**
 * Adds a priority level for a task.
 */
public class TaskSetPriorityCommand extends Command {

    private int index;
    private Task.Priority priorityLevel;

    /**
     * Instantiation of set priority command.
     *
     * @param index         index of task
     * @param priorityLevel priority level set for the task
     */
    public TaskSetPriorityCommand(int index, Task.Priority priorityLevel) {
        this.index = index;
        this.priorityLevel = priorityLevel;
    }

    /**
     * Sets priority level for a task.
     *
     * @return true
     */
    @Override
    public boolean execute(Model model) {
        try {
            TaskList taskList = model.getTaskList();
            String msg = taskList.setPriority(index, priorityLevel);
            if (!silent) {
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