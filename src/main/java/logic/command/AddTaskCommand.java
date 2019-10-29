package logic.command;

import model.Model;
import model.Task;
import common.DukeException;

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

    @Override
    public CommandOutput execute(Model model) throws DukeException {
        Task newTask = model.addTask(taskName);
        if (this.time != null) {
            newTask.setTime(this.time);
        }
        if (members != null) {
            //TODO add functionality to add members on the go
        }
        model.save();

        return new CommandOutput(FEEDBACK_MESSAGE + taskName);
    }
}