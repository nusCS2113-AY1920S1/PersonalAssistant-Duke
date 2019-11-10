package logic.command.edit;

import common.DukeException;
import logic.command.Command;
import logic.command.CommandOutput;
import model.Model;

public class EditMemberBioCommand extends Command {

    private static final String UPDATE_MSSAGE = "You have update the bio of member: ";
    private static final String SET_MSSAGE = "You have set the bio of member: ";
    private static final String NO_UPDATE_MSSAGE = "No update, they are the same.";
    private String memberName;
    private String bio;

    public EditMemberBioCommand(String memberName, String bio) {
        this.memberName = memberName;
        this.bio = bio;
    }


    //@@author yuyanglin28

    /**
     * This method is to edit member biography
     * @param model Model interface
     * @return set message or update message
     * @throws DukeException throw exception when there is no update
     */
    @Override
    public CommandOutput execute(Model model) throws DukeException {
        if (!checkMemberName(memberName, model)) {
            return new CommandOutput(Command.NAME_NOT_IN_MEMlIST_MESSAGE);
        } else {
            String oldbio = model.updateMemberBio(memberName, bio);

            if (oldbio == null) {
                model.save();
                return new CommandOutput(SET_MSSAGE + "[" + memberName + "]"
                        + " to " + "[[" + bio + "]]");
            } else if (oldbio.equals(bio)) {
                throw new DukeException(NO_UPDATE_MSSAGE);
            } else {
                model.save();
                return new CommandOutput(UPDATE_MSSAGE + "[" + memberName + "]"
                        + " from " + "[[" + oldbio + "]]" + " to " + "[[" + bio + "]]");
            }
        }

    }
}
