package logic.command.list;

import logic.command.Command;
import logic.command.CommandOutput;
import model.Model;
import model.Task;

import java.util.ArrayList;

import gui.Window;

public class  ListTasksCommand extends Command {

    static final String EMPTY_TASKS_LIST = "There are currently no tasks in project manager";
    private static final String SUCCESS_MESSAGE = "Here are all tasks in project manager:";

    //@@author yuyanglin28
    @Override
    public CommandOutput execute(Model model) {
        Window.instance.showTaskView(true);
        String tasks = model.getTasks();
        if (tasks.equals("")) {
            return new CommandOutput(EMPTY_TASKS_LIST);
        } else {
            return new CommandOutput(SUCCESS_MESSAGE + tasks);
        }
    }

}
