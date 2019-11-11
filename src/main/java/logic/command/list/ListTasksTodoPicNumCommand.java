package logic.command.list;

import gui.Window;
import logic.command.Command;
import logic.command.CommandOutput;
import model.Model;

public class ListTasksTodoPicNumCommand extends Command {

    public static final String SUCCESS_MESSAGE = "Here are todo tasks in order of num of PICs:";

    //@@author yuyanglin28
    /**
     * This method is to list todo tasks in order of number of PICs (person in charge)
     * @param model Model interface
     * @return a sorted todo task list in order of number of PICs
     */
    @Override
    public CommandOutput execute(Model model) {
        Window.instance.showTaskView(true);
        String tasks = model.tasksTodoInorderPicNum();
        if (tasks.equals("")) {
            return new CommandOutput(ListTasksTodoCommand.EMPTY_TASKS_LIST);
        } else {
            return new CommandOutput(SUCCESS_MESSAGE + tasks);
        }
    }
}
