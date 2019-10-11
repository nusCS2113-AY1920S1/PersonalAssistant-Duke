package seedu.duke.task.command;

import seedu.duke.CommandParser;
import seedu.duke.Duke;
import seedu.duke.task.TaskList;
import seedu.duke.common.command.Command;

public class TaskSetPriorityCommand extends Command {

    private TaskList taskList;
    private int index;
    private String priorityLevel;

    public TaskSetPriorityCommand(TaskList taskList, int index, String priorityLevel) {
        this.taskList = taskList;
        this.index = index;
        this.priorityLevel = priorityLevel;
    }

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