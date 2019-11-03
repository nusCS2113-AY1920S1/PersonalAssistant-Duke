package logic.command;

import common.DukeException;
import model.Model;

//@@ JasonChanWQ

public class RenameMemberCommand extends Command {

    private static final String SUCCESS_MESSAGE = " has been renamed to: ";
    public static final String INDEX_NOT_IN_MEMBER_lIST_MESSAGE = "Index is not within the member list";
    public int memberIndex;
    public String newName;

    public RenameMemberCommand(int memberIndex, String newName) {
        this.memberIndex = memberIndex;
        this.newName = newName;
    }

    //@@ JasonChanWQ

    @Override
    public CommandOutput execute(Model model) throws DukeException {
        if (memberIndex < 1 || memberIndex > model.getMemberListSize()) {
            return new CommandOutput(INDEX_NOT_IN_MEMBER_lIST_MESSAGE);
        } else {
            String oldName = model.getMemberList().get(memberIndex - 1).getName();
            model.getMemberList().get(memberIndex - 1).setName(newName);
            return new CommandOutput(oldName + SUCCESS_MESSAGE + newName);
        }
    }
}
