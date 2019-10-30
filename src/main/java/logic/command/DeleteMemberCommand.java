package logic.command;


import model.Member;
import model.Model;
import common.DukeException;

public class DeleteMemberCommand extends Command {
    public static final String FAIL_MESSAGE = "Fail to delete a member";
    private static final String SUCCESS_MSSAGE = "you have removed a member: ";
    private static final String INVALID_MSSAGE = " not exists.";
    private String memberName;

    public DeleteMemberCommand(String memberName) {
        this.memberName = memberName;
    }

    //@@author yuyanglin28
    @Override
    public CommandOutput execute(Model model) throws DukeException {
        try {
            if (model.deleteMember(memberName)) {
                return new CommandOutput(SUCCESS_MSSAGE + memberName);
            } else {
                return new CommandOutput(memberName + INVALID_MSSAGE);
            }

        } catch (Exception e) {
            throw new DukeException(FAIL_MESSAGE);
        }
    }
}
