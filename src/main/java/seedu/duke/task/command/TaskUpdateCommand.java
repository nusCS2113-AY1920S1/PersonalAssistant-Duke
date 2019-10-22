package seedu.duke.task.command;

import seedu.duke.CommandParseHelper;
import seedu.duke.Duke;
import seedu.duke.common.command.Command;
import seedu.duke.task.TaskList;
import seedu.duke.task.entity.Task;

import java.util.ArrayList;

/**
 * Edit command to change (or add) certain attributes of a task.
 */
public class TaskUpdateCommand extends Command {
    private int index;
    private ArrayList<String> descriptions;
    private ArrayList<TaskUpdateCommand.Attributes> attributes;

    /**
     * Instantiates a find command with all variables necessary. ==== BASE ====
     *
     * @param index        position of task as specified by input
     * @param descriptions what to modify to
     * @param attributes   what attribute to modify ==== BASE ====
     */
    TaskUpdateCommand(int index, ArrayList<String> descriptions,
                      ArrayList<TaskUpdateCommand.Attributes> attributes) {
        this.index = index;
        this.descriptions = descriptions;
        this.attributes = attributes;
    }

    /**
     * Executes the edit command.
     *
     * @return true if successful, false otherwise
     */
    @Override
    public boolean execute() {
        TaskList taskList = Duke.getModel().getTaskList();
        String msg = "";
        responseMsg = "";
        try {
            for (int i = 0; i < descriptions.size(); i++) {
                switch (attributes.get(i)) {
                case time:
                    msg = updateTime(taskList, i);
                    break;
                case doAfter:
                    msg = updateDoAfter(taskList, i);
                    break;
                case priority:
                    msg = updatePriority(taskList, i);
                    break;
                default:
                    msg = "Invalid attribute";
                    break;
                }
            }
            responseMsg += msg + "\n";
        } catch (CommandParseHelper.UserInputException e) {
            if (!silent) {
                Duke.getUI().showError(e.getMessage());
            }
            return false;
        }
        if (!silent) {
            Duke.getUI().showResponse(msg);
        }
        return true;
    }

    private String updatePriority(TaskList taskList, int i) throws CommandParseHelper.UserInputException {
        String msg;
        msg = taskList.setPriority(index, descriptions.get(i));
        return msg;
    }

    private String updateDoAfter(TaskList taskList, int i) throws CommandParseHelper.UserInputException {
        String msg;
        msg = taskList.setDoAfter(index, descriptions.get(i));
        return msg;
    }

    private String updateTime(TaskList taskList, int i) throws CommandParseHelper.UserInputException {
        String msg;
        if (taskList.get(index).getTaskType() == Task.TaskType.ToDo) {
            throw new CommandParseHelper.UserInputException("Time cannot be added to Todo task.");
        }
        msg = taskList.setTime(index, descriptions.get(i));
        return msg;
    }

    public enum Attributes {
        time, doAfter, priority
    }
}
