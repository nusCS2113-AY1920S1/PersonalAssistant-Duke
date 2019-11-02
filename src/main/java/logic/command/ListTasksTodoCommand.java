package logic.command;

import common.DukeException;
import model.Model;

public class ListTasksTodoCommand extends Command {

    public static final String EMPTY_TASKS_LIST = "There are currently no todo tasks in project manager.";
    private static final String SUCCESS_MESSAGE = "Here are todo tasks in project manager:";

    @Override
    public CommandOutput execute(Model model) {
        String tasks = model.getTodoTasks();
        if (tasks.equals("")) {
            return new CommandOutput(EMPTY_TASKS_LIST);
        } else {
            return new CommandOutput(SUCCESS_MESSAGE + tasks);
        }
    }
}
