package logic.command.list;

import logic.command.Command;
import logic.command.CommandOutput;
import model.Model;

public class ListTasksTodoPicNumCommand extends Command {

    public static final String SUCCESS_MESSAGE = "Here are todo tasks in order of num of PICs:";

    @Override
    public CommandOutput execute(Model model) {
        String tasks = model.tasksTodoInorderPicNum();
        if (tasks.equals("")) {
            return new CommandOutput(ListTasksTodoCommand.EMPTY_TASKS_LIST);
        } else {
            return new CommandOutput(SUCCESS_MESSAGE + tasks);
        }
    }
}
