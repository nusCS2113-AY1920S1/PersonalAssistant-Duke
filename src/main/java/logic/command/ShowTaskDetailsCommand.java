package logic.command;

import common.DukeException;
import model.Model;

//@@author JasonChanWQ

public class ShowTaskDetailsCommand extends Command {

    private static final String SUCCESS_MESSAGE = "Here are the details for Task: ";
    public static final String INPUT_INDEX_NOT_WITHIN_TASK_lIST_MESSAGE = " is not within the task list";
    public static final String NO_DESCRIPTION_MESSAGE = "Description: (Please input a description!)\n";
    public static final String NO_DEADLINE_MESSAGE = "Deadline: (No deadline assigned!)\n";
    public static final String NO_MEMBERS_ASSIGNED_MESSAGE = "Member(s) assigned: (No members assigned!)\n";
    public static final String NO_SKILLS_ASSIGNED_MESSAGE = "Skill(s) required: (No skills assigned!)\n";
    public static final String NO_REMINDER_SET_MESSAGE = "Reminder: (No reminder set!)\n";
    public int taskIndex;

    public ShowTaskDetailsCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public CommandOutput execute(Model model) throws DukeException {


        if (!model.isInTaskList(taskIndex)) {
            return new CommandOutput("Task: " + taskIndex + INPUT_INDEX_NOT_WITHIN_TASK_lIST_MESSAGE);
        } else {
            String output =  "Task Name: " + model.getTaskNameByIdOnList(taskIndex);
            output +=  " [" + model.getTaskIsDoneByIdOnList(taskIndex) + "]\n";

            if (model.getTaskDescriptionByIdOnList(taskIndex) != null) {
                output +=  "Description: " + model.getTaskDescriptionByIdOnList(taskIndex) + "\n";
            } else {
                output +=  NO_DESCRIPTION_MESSAGE;
            }
            if (model.getTaskDateTimeByIdOnList(taskIndex) != null) {
                output +=  "Deadline: " + model.getTaskDateTimeByIdOnList(taskIndex) + "\n";
            } else {
                output +=  NO_DEADLINE_MESSAGE;
            }
            if (model.getMemberListOfTaskByIdOnList(taskIndex).size() != 0) {
                output +=  "Member(s) assigned: " + model.getMemberListOfTaskByIdOnList(taskIndex) + "\n";
            } else {
                output +=  NO_MEMBERS_ASSIGNED_MESSAGE;
            }
            if (model.getSkillListOfTaskByIdOnList(taskIndex).size() != 0) {
                output +=  "Skill(s) required: " + model.getSkillListOfTaskByIdOnList(taskIndex) + "\n";
            } else {
                output +=  NO_SKILLS_ASSIGNED_MESSAGE;
            }
            if (model.getTaskReminderByIdOnList(taskIndex) != null) {
                output +=  "Reminder: " + model.getTaskReminderByIdOnList(taskIndex) + "\n";
            } else {
                output +=  NO_REMINDER_SET_MESSAGE;
            }

            return new CommandOutput(SUCCESS_MESSAGE + taskIndex + "\n" + output);
        }
    }

}