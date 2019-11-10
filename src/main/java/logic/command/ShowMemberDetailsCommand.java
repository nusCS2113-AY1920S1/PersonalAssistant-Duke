package logic.command;

import common.DukeException;
import model.Model;

//@@author JasonChanWQ

public class ShowMemberDetailsCommand extends Command {

    private static final String SUCCESS_MESSAGE = "Here are the details for Member: ";
    public static final String INPUT_MEMBER_NOT_WITHIN_MEMBER_lIST_MESSAGE = " is not within the member list";
    public static final String NO_BIOGRAPHY_MESSAGE = "Biography: (No biography saved!)\n";
    public static final String NO_EMAIL_MESSAGE = "Email: (No email saved!)\n";
    public static final String NO_PHONE_NUMBER_MESSAGE = "Phone: (No phone number saved!)\n";
    public static final String NO_TASK_ASSIGNED_MESSAGE = "Task(s) Assigned: (No tasks assigned!)\n";
    public static final String NO_SKILLS_SAVED_MESSAGE = "Skills: (No skills saved!)\n";
    public String memberName;

    public ShowMemberDetailsCommand(String memberName) {
        this.memberName = memberName;
    }

    @Override
    public CommandOutput execute(Model model) throws DukeException {

        if (!model.hasMember(memberName)) {
            return new CommandOutput("Member: " + memberName
                    + INPUT_MEMBER_NOT_WITHIN_MEMBER_lIST_MESSAGE);
        } else {
            String output =  "Member Name: " + memberName + "\n";

            if (model.getMemberBioByName(memberName) != null) {
                output +=  "Biography: " + model.getMemberBioByName(memberName) + "\n";
            } else {
                output += NO_BIOGRAPHY_MESSAGE;
            }
            if (model.getMemberEmailByName(memberName) != null) {
                output +=  "Email: " + model.getMemberEmailByName(memberName) + "\n";
            } else {
                output += NO_EMAIL_MESSAGE;
            }
            if (model.getMemberPhoneByName(memberName) != null) {
                output +=  "Phone: " + model.getMemberPhoneByName(memberName) + "\n";
            } else {
                output += NO_PHONE_NUMBER_MESSAGE;
            }
            if (model.getTaskListOfMemberByName(memberName).size() != 0) {
                output +=  "Task(s) assigned: " + model.getTaskListOfMemberByName(memberName) + "\n";
            } else {
                output += NO_TASK_ASSIGNED_MESSAGE;
            }
            if (model.getSkillListOfMemberByName(memberName).size() != 0) {
                output +=  "Skills: " + model.getSkillListOfMemberByName(memberName) + "\n";
            } else {
                output += NO_SKILLS_SAVED_MESSAGE;
            }
            return new CommandOutput(SUCCESS_MESSAGE + memberName + "\n" + output);
        }
    }
}
