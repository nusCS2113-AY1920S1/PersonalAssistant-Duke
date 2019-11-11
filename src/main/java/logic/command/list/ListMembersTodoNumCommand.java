package logic.command.list;

import common.DukeException;
import gui.Window;
import logic.command.Command;
import logic.command.CommandOutput;
import model.Model;

public class ListMembersTodoNumCommand extends Command {

    private static final String SUCCESS_MESSAGE = "Here are the members in order of num of todo tasks.";

    //@@author yuyanglin28

    /**
     * This method is to get the member list in order of number of todo tasks (small to big)
     * @param model Model interface
     * @return sorted member list in order of number of todo tasks
     */
    @Override
    public CommandOutput execute(Model model) throws DukeException {
        Window.instance.showTaskView(false);
        String members = model.membersInorderTodoNum();
        if (members.equals("")) {
            return new CommandOutput(ListMembersCommand.EMPTY_MEMBERS_LIST);
        } else {
            return new CommandOutput(SUCCESS_MESSAGE + members);
        }
    }
}
