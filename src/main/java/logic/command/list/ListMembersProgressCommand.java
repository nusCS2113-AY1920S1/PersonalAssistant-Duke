package logic.command.list;

import logic.command.Command;
import logic.command.CommandOutput;
import model.Model;

public class ListMembersProgressCommand extends Command {

    private static final String SUCCESS_MESSAGE = "Here are the members in order of progress.";

    //@@author yuyanglin28

    /**
     * This method is to get the member list in order of progress (high to low)
     * @param model Model interface
     * @return sorted member list in order of progress
     */
    @Override
    public CommandOutput execute(Model model) {
        String members = model.membersInorderProgress();
        if (members.equals("")) {
            return new CommandOutput(ListMembersCommand.EMPTY_MEMBERS_LIST);
        } else {
            return new CommandOutput(SUCCESS_MESSAGE + members);
        }
    }
}
