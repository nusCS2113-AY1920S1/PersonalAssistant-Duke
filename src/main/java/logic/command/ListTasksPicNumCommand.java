package logic.command;

import model.Model;

public class ListTasksPicNumCommand extends Command {

    private static final String SUCCESS_MESSAGE = "Here are all the tasks in order of num of PICs:";

    /**
     * This method is to execute command: list tasks all picnum
     * @param model model part
     * @return CommandOutput, a string
     */
    @Override
    public CommandOutput execute(Model model) {
        String tasks = model.tasksAllInorderPicNum();
        if (tasks.equals("")) {
            return new CommandOutput(ListTasksCommand.EMPTY_TASKS_LIST);
        } else {
            return new CommandOutput(SUCCESS_MESSAGE + tasks);
        }
    }
}
