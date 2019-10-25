package logic.command;

import model.Model;
import model.Task;
import utils.DukeException;

public class DeleteTaskCommand extends Command {
    public static final String COMMAND_WORD = "task";
    private String taskName;

    public DeleteTaskCommand(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public CommandOutput execute(Model model) throws DukeException {
        Task temp = model.deleteTask(taskName);
        return new CommandOutput("you have removed a task: " + temp.getName());
    }

}
