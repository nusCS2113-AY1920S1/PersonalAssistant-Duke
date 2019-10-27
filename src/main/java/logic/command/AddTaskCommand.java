package logic.command;
import model.Model;
import utils.DukeException;

import java.util.Date;

public class AddTaskCommand extends Command {
    public static final String COMMAND_WORD = "task";
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
        model.addTask(taskName);
        return new CommandOutput(FEEDBACK_MESSAGE + taskName);
    }
}
