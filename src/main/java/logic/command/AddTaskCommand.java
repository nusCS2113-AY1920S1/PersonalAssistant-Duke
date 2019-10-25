package logic.command;
import model.Model;
import utils.DukeException;

public class AddTaskCommand extends Command {
    public static final String COMMAND_WORD = "task";
    private String taskName;

    public AddTaskCommand(String arguments) {
        taskName = arguments;
    }

    @Override
    public CommandOutput execute(Model model) throws DukeException {
        model.addTask(taskName);
        return new CommandOutput("you have created a new Task: " + taskName);
    }
}
