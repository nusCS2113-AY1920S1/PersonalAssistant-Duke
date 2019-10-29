package logic.command;

import core.Duke;
import model.Model;
import model.Task;
import common.DukeException;

public class DeleteTaskCommand extends Command {

    private int taskIndexInList;

    public DeleteTaskCommand(int index) {
        this.taskIndexInList = index;
    }

    @Override
    public CommandOutput execute(Model model) throws DukeException {

        if (taskIndexInList > model.getTaskListSize() || taskIndexInList < 1) {
            return new CommandOutput("invalid task index");
        } else {
            Task temp = model.deleteTask(taskIndexInList);
            return new CommandOutput("you have removed a task: " + temp.getName());
        }


    }

}
