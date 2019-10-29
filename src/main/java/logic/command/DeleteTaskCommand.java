package logic.command;

import model.Member;
import model.Model;
import model.Task;
import utils.DukeException;

public class DeleteTaskCommand extends Command {

    private int taskIndex;

    public DeleteTaskCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public CommandOutput execute(Model model) throws DukeException {
        model.deleteTaskInMember(taskIndex);
        Task temp = model.deleteTask(taskIndex);
        return new CommandOutput("you have removed a task: " + temp.getName());
    }

}
