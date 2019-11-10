package command;

import common.DukeException;
import logic.command.Command;
import logic.command.CommandOutput;
import logic.parser.ShowCommandParser;
import model.Model;
import model.ModelController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author JasonChanWQ

public class ShowMemberDetailsCommandTest {

    @Test
    public void showMemberDetailsCommand_inputMemberNotWithinMemberList_throwsException() throws DukeException {
        Model model = new ModelController();
        for (int i = model.getMemberListSize() - 1; i >= 0; i--)  {
            model.deleteMember(i);
        }
        model.addMember("John");
        Command command = ShowCommandParser.parseShowCommand("member Jimmy");
        assertThrows(DukeException.class, () -> command.execute(model));
    }

    @Test
    public void showMemberDetailsCommandSuccess_checkUneditedMember() throws DukeException {
        Model model = new ModelController();
        for (int i = model.getMemberListSize() - 1; i >= 0; i--)  {
            model.deleteMember(i);
        }
        model.addMember("John");
        Command command = ShowCommandParser.parseShowCommand("member John");
        CommandOutput out = command.execute(model);
        assertEquals("Here are the details for Member: John\n" + "Member Name: John\n" +
                "Biography: (No biography saved!)\n" + "Email: (No email saved!)\n" +
                "Phone: (No phone number saved!)\n" + "Task(s) Assigned: (No tasks assigned!)\n" +
                "Skills: (No skills saved!)\n", out.getOutputToUser());
    }

    @Test
    public void showMemberDetailsCommandSuccess_checkEditedMember() throws DukeException {
        Model model = new ModelController();
        for (int i = model.getMemberListSize() - 1; i >= 0; i--)  {
            model.deleteMember(i);
        }
        for (int i = model.getTaskListSize() - 1; i >= 0; i--)  {
            model.deleteTask(i);
        }

        model.addMember("John");
        model.addTask("Complete event poster");
        model.updateMemberBio(0,"Test Bio");
        model.updateMemberEmail(0,"abc@gmail.com");
        model.updateMemberPhone(0, "98761234");
        model.link(0,"John");
        model.addMemberSkill("John","Java");
        Command command = ShowCommandParser.parseShowCommand("member John");
        CommandOutput out = command.execute(model);
        assertEquals("Here are the details for Member: John\n" + "Member Name: John\n" +
                "Biography: Test Bio\n" + "Email: abc@gmail.com\n" +
                "Phone: 98761234\n" + "Task(s) Assigned: [Complete event poster]\n" +
                "Skills: [Java]\n", out.getOutputToUser());

    }
}
