package seedu.duke.task.command;

import seedu.duke.common.parser.CommandParseHelper;
import seedu.duke.common.command.Command;
import seedu.duke.common.model.Model;
import seedu.duke.task.TaskList;
import seedu.duke.task.entity.Task;
import seedu.duke.task.parser.TaskCommandParseHelper;
import seedu.duke.ui.UI;

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
    public TaskUpdateCommand(int index, ArrayList<String> descriptions,
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
    public boolean execute(Model model) {
        TaskList taskList = model.getTaskList();
        String msg = "";
        responseMsg = "";
        try {
            for (int i = 0; i < descriptions.size(); i++) {
                switch (attributes.get(i)) {
                case TIME:
                    msg = updateTime(taskList, i);
                    break;
                case DO_AFTER:
                    msg = updateDoAfter(taskList, i);
                    break;
                case PRIORITY:
                    msg = updatePriority(taskList, i);
                    break;
                case TAG:
                    msg = updateTags(taskList, i);
                    i = descriptions.size();
                    // tags will be the last entries in descriptions ArrayList and updateTags will handle
                    // them all. So skip to the end.
                    break;
                default:
                    msg = "Invalid attribute";
                    break;
                }
            }
            responseMsg += msg + System.lineSeparator();
        } catch (CommandParseHelper.CommandParseException e) {
            if (!silent) {
                UI.getInstance().showError(e.getMessage());
            }
            return false;
        }
        if (!silent) {
            UI.getInstance().showResponse(msg);
        }
        return true;
    }

    private String updatePriority(TaskList taskList, int i) throws CommandParseHelper.CommandParseException {
        String msg;
        Task.Priority level = TaskCommandParseHelper.getPriorityLevel(descriptions.get(i));
        msg = taskList.setPriority(index, level);
        return msg;
    }

    private String updateDoAfter(TaskList taskList, int i) throws CommandParseHelper.CommandParseException {
        String msg;
        msg = taskList.setDoAfter(index, descriptions.get(i));
        return msg;
    }

    private String updateTime(TaskList taskList, int i) throws CommandParseHelper.CommandParseException {
        String msg;
        if (taskList.get(index).getTaskType() == Task.TaskType.TODO) {
            throw new CommandParseHelper.CommandParseException("Time cannot be added to Todo task.");
        }
        msg = taskList.setTime(index, descriptions.get(i));
        return msg;
    }

    private String updateTags(TaskList taskList, int i) throws CommandParseHelper.CommandParseException {
        String msg;
        ArrayList<String> tags = new ArrayList<>();
        for (int j = i; j < descriptions.size(); j++) {
            if (attributes.get(j).equals(Attributes.TAG)) {
                tags.add(descriptions.get(j));
            }
        }
        msg = taskList.setTags(index, tags);
        return msg;
    }

    public enum Attributes {
        TIME, DO_AFTER, PRIORITY, TAG
    }
}