package logic.command;

import common.DukeException;
import logic.command.Command;
import logic.command.CommandOutput;
import model.Member;
import model.Model;
import model.Task;

//@@author JasonChanWQ

public class ShowTaskDetailsCommand extends Command {

    private static final String SUCCESS_MESSAGE = "Here are the details for Task: ";
    public static final String INPUT_INDEX_NOT_WITHIN_TASK_lIST_MESSAGE = " is not within the task list";
    public int indexOfTask;

    public ShowTaskDetailsCommand(int indexOfTask) {
        this.indexOfTask = indexOfTask;
    }

    @Override
    public CommandOutput execute(Model model) throws DukeException {


        if (indexOfTask < 1 || indexOfTask > model.getTaskListSize()) {
            return new CommandOutput("Task: " + indexOfTask + INPUT_INDEX_NOT_WITHIN_TASK_lIST_MESSAGE);
        } else {
            String output = "";
            output +=  "Task Name: " + model.getTaskNameById(indexOfTask - 1) + "\n";

            output +=  "Deadline: " + model.getTaskDateTimeById(indexOfTask - 1) + "\n";


            return new CommandOutput(SUCCESS_MESSAGE + indexOfTask + "\n" + output);
        }
    }

}