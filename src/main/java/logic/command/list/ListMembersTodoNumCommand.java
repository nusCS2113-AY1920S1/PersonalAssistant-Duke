package logic.command.list;

import common.DukeException;
import logic.command.Command;
import logic.command.CommandOutput;
import model.Model;

public class ListMembersTodoNumCommand extends Command {

    private static final String SUCCESS_MESSAGE = "Here are the members in order of num of todo tasks.";

    @Override
    public CommandOutput execute(Model model) throws DukeException {
        String members = model.membersInorderTodoNum();
        if (members.equals("")) {
            return new CommandOutput(ListMembersCommand.EMPTY_MEMBERS_LIST);
        } else {
            return new CommandOutput(SUCCESS_MESSAGE + members);
        }
    }
}
