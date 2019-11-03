package logic.command;

import common.DukeException;
import model.Member;
import model.Model;

//@@author JasonChanWQ

public class ShowMemberDetailsCommand extends Command {

    private static final String SUCCESS_MESSAGE = "Here are the details for Member: ";
    public static final String INPUT_MEMBER_NOT_WITHIN_MEMBER_lIST_MESSAGE = " is not within the member list";
    public String memberName;

    public ShowMemberDetailsCommand(String memberName) {
        this.memberName = memberName;
    }

    @Override
    public CommandOutput execute(Model model) throws DukeException {

        Member member = new Member(memberName);

        if (!model.getMemberList().contains(member)) {
            return new CommandOutput("Member: " + memberName
                    + INPUT_MEMBER_NOT_WITHIN_MEMBER_lIST_MESSAGE);
        } else {
            int index = model.getMemberList().indexOf(member);
            String output = "";
            output +=  "Member Name: " + memberName + "\n";
            if (model.getMemberList().get(index).getBiography() != null) {
                output +=  "Biography: " + model.getMemberList().get(index).getBiography() + "\n";
            }
            if (model.getMemberList().get(index).getEmail() != null) {
                output +=  "Email: " + model.getMemberList().get(index).getEmail() + "\n";
            }
            if (model.getMemberList().get(index).getPhone() != null) {
                output +=  "Phone: " + model.getMemberList().get(index).getPhone() + "\n";
            }
            if (model.getMemberList().get(index).getTaskList().size() != 0) {
                output +=  "Task(s) assigned: " + model.getMemberList().get(index).getTaskList() + "\n";
            }
            if (model.getMemberList().get(index).getSkillList().size() != 0) {
                output +=  "Skills: " + model.getMemberList().get(index).getSkillList() + "\n";
            }
            return new CommandOutput(SUCCESS_MESSAGE + memberName + "\n" + output);
        }
    }
}
