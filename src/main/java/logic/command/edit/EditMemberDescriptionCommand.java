package logic.command.edit;

import common.DukeException;
import logic.command.Command;
import logic.command.CommandOutput;
import model.Member;
import model.Model;
import model.Task;

//@@author JasonChanWQ

public class EditMemberDescriptionCommand extends Command {

    private static final String SUCCESS_MESSAGE = " has been renamed to: ";
    public static final String INPUT_NAME_NOT_IN_MEMBER_lIST_MESSAGE = " is not within the member list!";
    public static final String INPUT_NAME_ALREADY_IN_MEMBER_lIST_MESSAGE = " already exists within the member list!";
    public String oldName;
    public String newName;

    public EditMemberDescriptionCommand(String oldName, String newName) {
        this.oldName = oldName;
        this.newName = newName;
    }

    @Override
    public CommandOutput execute(Model model) throws DukeException {
        int memberIndex = 0;
        for (int i = 0; i < model.getMemberListSize(); i++) {
            if (oldName.equals(model.getMemberNameById(i))) {
                memberIndex = i;
                break;
            }
        }
        Member oldMember = new Member(oldName);
        Member newMember = new Member(newName);

        if (!model.getMemberList().contains(oldMember)) {
            return new CommandOutput(oldName + INPUT_NAME_NOT_IN_MEMBER_lIST_MESSAGE);
        } else if (model.getMemberList().contains(newMember)) {
            return new CommandOutput(newName + INPUT_NAME_ALREADY_IN_MEMBER_lIST_MESSAGE);
        } else {

            model.getMemberList().get(memberIndex).setName(newName);

            for (Task task : model.getTaskList()) {
                if (task.hasMember(oldName)) {
                    task.updateMember(oldName, newName);
                }
            }

            return new CommandOutput(oldName + SUCCESS_MESSAGE + newName);
        }
    }

}
