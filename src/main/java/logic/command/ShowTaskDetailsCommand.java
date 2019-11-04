package logic.command;

import common.DukeException;
import model.Model;

//@@author JasonChanWQ

public class ShowTaskDetailsCommand extends Command {

    private static final String SUCCESS_MESSAGE = "Here are the details for Task: ";
    public static final String INPUT_INDEX_NOT_WITHIN_TASK_lIST_MESSAGE = " is not within the task list";
    public int taskIndex;

    public ShowTaskDetailsCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public CommandOutput execute(Model model) throws DukeException {


        if (!model.isInTaskList(taskIndex)) {
            return new CommandOutput("Task: " + taskIndex + INPUT_INDEX_NOT_WITHIN_TASK_lIST_MESSAGE);
        } else {
            String output = "";
            output +=  "Task Name: " + model.getTaskNameById(taskIndex - 1);
            output +=  " [" + model.getTaskList().get(taskIndex - 1).getStatusIcon() + "]\n";
            if (model.getTaskDateTimeById(taskIndex - 1) != null) {
                output +=  "Deadline: " + model.getTaskDateTimeById(taskIndex - 1) + "\n";
            }
            if (model.getTaskList().get(taskIndex - 1).getMemberList().size() != 0) {
                output +=  "Member(s) assigned: " + model.getTaskList().get(taskIndex - 1).getMemberList() + "\n";
            }

            return new CommandOutput(SUCCESS_MESSAGE + taskIndex + "\n" + output);
        }
    }

}