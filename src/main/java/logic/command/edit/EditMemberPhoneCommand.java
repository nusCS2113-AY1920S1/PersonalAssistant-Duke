package logic.command.edit;

import common.DukeException;
import logic.command.Command;
import logic.command.CommandOutput;
import model.Model;

public class EditMemberPhoneCommand extends Command {

    private static final String UPDATE_MSSAGE = "You have update the phone of member: ";
    private static final String SET_MSSAGE = "You have set the phone of member: ";
    private static final String NO_UPDATE_MSSAGE = "No update, they are the same.";
    private String memberName;
    private String phone;

    public EditMemberPhoneCommand(String memberName, String phone) {
        this.memberName = memberName;
        this.phone = phone;
    }


    //@@author yuyanglin28

    /**
     * This method is to edit member phone
     * @param model Model interface
     * @return set message or update message
     * @throws DukeException throw exception when member name not in member list or wrong phone format
     */
    @Override
    public CommandOutput execute(Model model) throws DukeException {
        if (!checkMemberName(memberName, model)) {
            throw new DukeException(memberName + Command.NAME_NOT_IN_MEMlIST_MESSAGE);
        } else {
            String oldphone = model.updateMemberPhone(memberName, phone);

            if (oldphone == null) {
                model.save();
                return new CommandOutput(SET_MSSAGE + "[" + memberName + "]"
                        + " to " + "[[" + phone + "]]");
            } else if (oldphone.equals(phone)) {
                return new CommandOutput(NO_UPDATE_MSSAGE);
            } else {
                model.save();
                return new CommandOutput(UPDATE_MSSAGE + "[" + memberName + "]"
                        + " from " + "[[" + oldphone + "]]" + " to " + "[[" + phone + "]]");
            }
        }

    }
}
