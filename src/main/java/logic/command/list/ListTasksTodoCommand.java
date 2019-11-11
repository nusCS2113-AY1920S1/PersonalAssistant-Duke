package logic.command.list;

import common.DukeException;
import gui.Window;
import logic.command.Command;
import logic.command.CommandOutput;
import model.Model;

public class ListTasksTodoCommand extends Command {

    public static final String EMPTY_TASKS_LIST = "There are currently no todo tasks in project manager.";
    private static final String SUCCESS_MESSAGE = "Here are todo tasks in project manager:";

    //@@author yuyanglin28

    /**
     * This method is to list all todo tasks
     * @param model Model interface
     * @return todo task list, no special order
     */
    @Override
    public CommandOutput execute(Model model) {
        Window.instance.showTaskView(true);
        String tasks = model.getTodoTasks();
        if (tasks.equals("")) {
            return new CommandOutput(EMPTY_TASKS_LIST);
        } else {
            return new CommandOutput(SUCCESS_MESSAGE + tasks);
        }
    }
}
