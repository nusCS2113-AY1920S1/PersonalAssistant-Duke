package seedu.duke.task.command;

import seedu.duke.CommandParser;
import seedu.duke.Duke;
import seedu.duke.common.command.Command;
import seedu.duke.task.TaskList;

public class TaskEditCommand extends Command {
    private TaskList taskList;
    private int index;
    private String description;
    private TaskList.Attributes attribute;

    public TaskEditCommand(TaskList taskList, int index, String description, TaskList.Attributes attribute) {
        this.taskList = taskList;
        this.index = index;
        this.description = description;
        this.attribute = attribute;
    }

    @Override
    public boolean execute() {
        String msg = "";
        try {
            msg = taskList.setAttributes(index, description, attribute);
        } catch (CommandParser.UserInputException e) {
            Duke.getUI().showError(e.getMessage());
            return false;
        }
        Duke.getUI().showResponse(msg);
        return true;
    }
}
