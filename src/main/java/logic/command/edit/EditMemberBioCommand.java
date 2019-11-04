package logic.command.edit;

import common.DukeException;
import logic.command.Command;
import logic.command.CommandOutput;
import model.Model;

public class EditMemberBioCommand extends Command {

    private static final String UPDATE_MSSAGE = "you have update the bio of member: ";
    private static final String SET_MSSAGE = "you have set the bio of member: ";
    private static final String NO_UPDATE_MSSAGE = "no update, they are the same.";
    public static final String INDEX_NOT_IN_MEMlIST_MESSAGE = "Index is not within the memberlist list";
    private int memberIndexInList;
    private String bio;

    public EditMemberBioCommand(int memberIndexInList, String bio) {
        this.memberIndexInList = memberIndexInList;
        this.bio = bio;
    }


    @Override
    public CommandOutput execute(Model model) throws DukeException {
        if (!checkMemberIndex(memberIndexInList, model)) {
            return new CommandOutput(INDEX_NOT_IN_MEMlIST_MESSAGE);
        } else {
            String oldbio = model.updateMemberBio(memberIndexInList - 1, bio);
            String memberName = model.getMemberNameById(memberIndexInList - 1);
            if (oldbio == null) {
                model.save();
                return new CommandOutput(SET_MSSAGE + "[" + memberName + "]"
                        + " to " + "[" + bio + "]");
            } else if (oldbio.equals(bio)) {
                throw new DukeException(NO_UPDATE_MSSAGE);
            } else {
                model.save();
                return new CommandOutput(UPDATE_MSSAGE + "[" + memberName + "]"
                        + " from " + "[" + oldbio + "]" + " to " + "[" + bio + "]");
            }
        }

    }
}
