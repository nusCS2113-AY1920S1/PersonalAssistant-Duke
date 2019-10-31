package logic.command;

import model.Model;
import model.Task;
import common.DukeException;
import common.LoggerController;

import java.util.Date;

public class AddTaskCommand extends Command {

    public static final String FEEDBACK_MESSAGE = "You have created a new task: ";
    private String taskName;
    private String members;
    private Date time;

    public AddTaskCommand(String taskName) {
        this.taskName = taskName;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    //@@author JustinChia1997
    @Override
    public CommandOutput execute(Model model) throws DukeException {
        Task newTask = model.addTask(taskName);
        if (this.time != null) {
            newTask.setTime(this.time);
        }
        String memberMissingMessage = "";
        if (members != null) {
            String[] membersString = members.split(" ");
            for (int i = 0; i < membersString.length; i++) {
                if (model.getMemberManager().hasMember(membersString[i])) {
                    newTask.addMember(membersString[i]);
                } else {
                    memberMissingMessage += "Warning: member " + membersString[i] + " do not exist.\n";
                }
            }
        }
        model.save();

        if (model.hasTask(taskName)) {
            LoggerController.logInfo(AddTaskCommand.class, "Task "
                    + taskName + " has been added successfully");
        } else {
            LoggerController.logDebug(AddTaskCommand.class, "Task not added");
        }
        return new CommandOutput(memberMissingMessage + FEEDBACK_MESSAGE + taskName);
    }
}