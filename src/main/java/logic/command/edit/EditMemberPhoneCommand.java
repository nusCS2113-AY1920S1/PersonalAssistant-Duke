package logic.command.edit;

import common.DukeException;
import logic.command.Command;
import logic.command.CommandOutput;
import model.Model;

public class EditMemberPhoneCommand extends Command {

    private static final String UPDATE_MSSAGE = "you have update the phone of member: ";
    private static final String SET_MSSAGE = "you have set the phone of member: ";
    private static final String NO_UPDATE_MSSAGE = "no update, they are the same.";
    public static final String INDEX_NOT_IN_MEMlIST_MESSAGE = "Index is not within the memberlist list";
    private int memberIndexInList;
    private String phone;

    public EditMemberPhoneCommand(int memberIndexInList, String phone) {
        this.memberIndexInList = memberIndexInList;
        this.phone = phone;
    }


    @Override
    public CommandOutput execute(Model model) throws DukeException {
        if (!checkMemberIndex(memberIndexInList, model)) {
            return new CommandOutput(INDEX_NOT_IN_MEMlIST_MESSAGE);
        } else {
            String oldphone = model.updateMemberPhone(memberIndexInList - 1, phone);
            String memberName = model.getMemberNameById(memberIndexInList - 1);
            if (oldphone == null) {
                model.save();
                return new CommandOutput(SET_MSSAGE + "[" + memberName + "]"
                        + " to " + "[" + phone + "]");
            } else if (oldphone.equals(phone)) {
                throw new DukeException(NO_UPDATE_MSSAGE);
            } else {
                model.save();
                return new CommandOutput(UPDATE_MSSAGE + "[" + memberName + "]"
                        + " from " + "[" + oldphone + "]" + " to " + "[" + phone + "]");
            }
        }

    }
}
