package seedu.duke.task.command;

import seedu.duke.CommandParser;
import seedu.duke.Duke;
import seedu.duke.common.command.Command;
import seedu.duke.task.TaskList;

/**
 * Edit command to change (or add) certain attributes of a task.
 */
public class TaskEditCommand extends Command {
    private TaskList taskList;
    private int index;
    private String description;
    private TaskEditCommand.Attributes attribute;

    /**
     * Instantiates a find command with all variables necessary.
     * @param taskList      list of tasks
     * @param index         position of task as specified by input
     * @param description   what to modify to
     * @param attribute     what attribute to modify
     */
    public TaskEditCommand(TaskList taskList, int index, String description, TaskEditCommand.Attributes attribute) {
        this.taskList = taskList;
        this.index = index;
        this.description = description;
        this.attribute = attribute;
    }

    public enum Attributes {
        time, doAfter, priority
    }

    /**
     * Executes the edit command.
     * @return true if successful, false otherwise
     */
    @Override
    public boolean execute() {
        String msg = "";
        try {
            switch(attribute) {
            case time:
                msg = taskList.setTime(index, description);
                break;
            case doAfter:
                msg = taskList.setDoAfter(index, description);
                break;
            case priority:
                msg = taskList.setPriority(index, description);
                break;
            default:
                msg = "Invalid attribute";
                break;
            }
        } catch (CommandParser.UserInputException e) {
            Duke.getUI().showError(e.getMessage());
            return false;
        }
        Duke.getUI().showResponse(msg);
        return true;
    }
}
