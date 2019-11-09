package logic.command.edit;

import common.DukeException;
import logic.command.Command;
import logic.command.CommandOutput;
import model.Member;
import model.Model;
import model.Task;

//@@author JasonChanWQ

public class EditTaskNameCommand extends Command {

    private static final String SUCCESS_MESSAGE = " has been renamed to: ";
    public static final String INPUT_INDEX_NOT_WITHIN_TASK_lIST_MESSAGE = " is not within the task list!";
    public static final String INPUT_NAME_ALREADY_IN_TASK_lIST_MESSAGE = " already exists within the task list!";
    public int taskIndex;
    public String newName;

    public EditTaskNameCommand(int taskIndex, String newName) {
        this.taskIndex = taskIndex;
        this.newName = newName;
    }

    @Override
    public CommandOutput execute(Model model) throws DukeException {
        Task newTask = new Task(newName);



        if (!model.isInTaskList(taskIndex)) {
            return new CommandOutput("Index: " + taskIndex + INPUT_INDEX_NOT_WITHIN_TASK_lIST_MESSAGE);
        } else if (model.getTaskList().contains(newTask)) {
            String oldName = model.getTaskList().get(taskIndex - 1).getName();
            return new CommandOutput("Task: " + oldName + INPUT_NAME_ALREADY_IN_TASK_lIST_MESSAGE);
        } else {
            String oldName = model.getTaskList().get(taskIndex - 1).getName();

            model.getTaskList().get(taskIndex - 1).setName(newName);

            for (Member member : model.getMemberList()) {
                if (member.hasTask(oldName)) {
                    member.updateTask(oldName, newName);
                }
            }

            return new CommandOutput(oldName + SUCCESS_MESSAGE + newName);
        }
    }

}