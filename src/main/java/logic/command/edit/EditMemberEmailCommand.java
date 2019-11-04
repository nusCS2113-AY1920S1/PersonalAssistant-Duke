package logic.command.edit;

import common.DukeException;
import logic.command.Command;
import logic.command.CommandOutput;
import model.Model;

public class EditMemberEmailCommand extends Command {

    private static final String UPDATE_MSSAGE = "you have update the email of member: ";
    private static final String SET_MSSAGE = "you have set the email of member: ";
    private static final String NO_UPDATE_MSSAGE = "no update, they are the same.";
    public static final String INDEX_NOT_IN_MEMlIST_MESSAGE = "Index is not within the memberlist list";
    private int memberIndexInList;
    private String email;

    public EditMemberEmailCommand(int memberIndexInList, String email) {
        this.memberIndexInList = memberIndexInList;
        this.email = email;
    }


    @Override
    public CommandOutput execute(Model model) throws DukeException {
        if (!checkMemberIndex(memberIndexInList, model)) {
            return new CommandOutput(INDEX_NOT_IN_MEMlIST_MESSAGE);
        } else {
            String oldemail = model.updateMemberEmail(memberIndexInList - 1, email);
            String memberName = model.getMemberNameById(memberIndexInList - 1);
            if (oldemail == null) {
                model.save();
                return new CommandOutput(SET_MSSAGE + "[" + memberName + "]"
                        + " to " + "[" + email + "]");
            } else if (oldemail.equals(email)) {
                throw new DukeException(NO_UPDATE_MSSAGE);
            } else {
                model.save();
                return new CommandOutput(UPDATE_MSSAGE + "[" + memberName + "]"
                        + " from " + "[" + oldemail + "]" + " to " + "[" + email + "]");
            }
        }

    }

   
}
