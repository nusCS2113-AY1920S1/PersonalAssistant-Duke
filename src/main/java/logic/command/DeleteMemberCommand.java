package logic.command;


import model.Member;
import model.Model;
import utils.DukeException;

public class DeleteMemberCommand extends Command{

    private String memberName;

    public DeleteMemberCommand(String memberName) {
        this.memberName = memberName;
    }

    @Override
    public CommandOutput execute(Model model) throws DukeException {
        Member temp = model.deleteMember(memberName);
        return new CommandOutput("you have removed a member: " + temp.getName());
    }
}
