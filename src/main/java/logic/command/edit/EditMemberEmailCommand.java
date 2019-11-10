package logic.command.edit;

import common.DukeException;
import logic.command.Command;
import logic.command.CommandOutput;
import model.Model;

public class EditMemberEmailCommand extends Command {

    private static final String UPDATE_MSSAGE = "You have update the email of member: ";
    private static final String SET_MSSAGE = "You have set the email of member: ";
    private static final String NO_UPDATE_MSSAGE = "No update, they are the same.";
    private String memberName;
    private String email;

    public EditMemberEmailCommand(String memberName, String email) {
        this.memberName = memberName;
        this.email = email;
    }


    //@@author yuyanglin28

    /**
     * This method is to edit member email
     * @param model Model interface
     * @return set message or update message
     * @throws DukeException throw exception when there is no update or the email format is not correct
     */
    @Override
    public CommandOutput execute(Model model) throws DukeException {
        if (!checkMemberName(memberName, model)) {
            return new CommandOutput(memberName + Command.NAME_NOT_IN_MEMlIST_MESSAGE);
        } else {
            String oldemail = model.updateMemberEmail(memberName, email);

            if (oldemail == null) {
                model.save();
                return new CommandOutput(SET_MSSAGE + "[" + memberName + "]"
                        + " to " + "[[" + email + "]]");
            } else if (oldemail.equals(email)) {
                throw new DukeException(NO_UPDATE_MSSAGE);
            } else {
                model.save();
                return new CommandOutput(UPDATE_MSSAGE + "[" + memberName + "]"
                        + " from " + "[[" + oldemail + "]]" + " to " + "[[" + email + "]]");
            }
        }

    }

   
}
