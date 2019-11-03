package logic.command.delete;


import core.Duke;
import logic.command.Command;
import logic.command.CommandOutput;
import model.Model;
import common.DukeException;

public class DeleteMemberCommand extends Command {
    public static final String FAIL_MESSAGE = "Fail to delete a member";
    private static final String SUCCESS_MSSAGE = "you have removed a member: ";
    private static final String INVALID_MSSAGE = " is an invalid index.";
    private int[] memberIndexInList;
    private String memberName;

    public DeleteMemberCommand(int[] memberIndexInList) {
        this.memberIndexInList = memberIndexInList;
    }

    //@@author yuyanglin28
    @Override
    public CommandOutput execute(Model model) throws DukeException {
        String output = "";
        try {
            for (int i = 0; i < memberIndexInList.length; i++) {
                if (checkMemberIndex(memberIndexInList[i], model)) {
                    memberName = model.deleteMember(memberIndexInList[i] - 1);
                    model.save();
                    output += SUCCESS_MSSAGE + memberName + "\n";
                } else {
                    output += memberIndexInList[i] + INVALID_MSSAGE + "\n";
                }

            }
            return new CommandOutput(output);

        } catch (Exception e) {
            throw new DukeException(FAIL_MESSAGE);
        }
    }
}
