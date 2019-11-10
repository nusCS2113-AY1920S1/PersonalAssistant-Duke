package logic.command.delete;


import core.Duke;
import logic.command.Command;
import logic.command.CommandOutput;
import model.Model;
import common.DukeException;

public class DeleteMemberCommand extends Command {

    private static final String SUCCESS_MSSAGE = "You have removed a member: ";

    private String[] memberNames;

    public DeleteMemberCommand(String[] memberNames) {
        this.memberNames = memberNames;
    }

    //@@author yuyanglin28

    /**
     * This method is to delete the member with some member names
     * @param model Model interface
     * @return successful message or name doesn't in member list message
     */
    @Override
    public CommandOutput execute(Model model) {
        String output = "";

        for (int i = 0; i < memberNames.length; i++) {
            if (checkMemberName(memberNames[i], model)) {
                model.deleteMember(memberNames[i]);
                model.save();
                output += SUCCESS_MSSAGE + memberNames[i] + "\n";
            } else {
                output += memberNames[i] + Command.NAME_NOT_IN_MEMlIST_MESSAGE + "\n";
            }

        }
        return new CommandOutput(output);

    }
}
