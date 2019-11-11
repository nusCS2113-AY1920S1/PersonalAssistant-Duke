package logic.command;

import common.DukeException;
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
        model.getMemberList().clear();
        model.addMember("John");
        Command command = ShowCommandParser.parseShowCommand("member Jimmy");
        assertThrows(DukeException.class, () -> command.execute(model));
    }

    @Test
    public void showMemberDetailsCommandSuccess_checkUneditedMember() throws DukeException {
        Model model = new ModelController();
        model.getMemberList().clear();
        model.addMember("John");
        Command command = ShowCommandParser.parseShowCommand("member John");
        CommandOutput out = command.execute(model);
        assertEquals("Here are the details for Member: John\n"
                + "Member Name: John\n"
                + "Biography: (No biography saved!)\n"
                + "Email: (No email saved!)\n"
                + "Phone: (No phone number saved!)\n"
                + "Task(s) Assigned: (No tasks assigned!)\n"
                + "Skills: (No skills saved!)\n", out.getOutputToUser());
    }

    @Test
    public void showMemberDetailsCommandSuccess_checkEditedMember() throws DukeException {
        Model model = new ModelController();
        model.getMemberList().clear();
        model.getTaskList().clear();

        model.addMember("John");
        model.addTask("Complete event poster");
        model.updateMemberBio("John","Test Bio");
        model.updateMemberEmail("John","abc@gmail.com");
        model.updateMemberPhone("John", "98761234");
        model.link(0,"John");
        model.addMemberSkill("John","Java");
        Command command = ShowCommandParser.parseShowCommand("member John");
        CommandOutput out = command.execute(model);
        assertEquals("Here are the details for Member: John\n"
                + "Member Name: John\n"
                + "Biography: Test Bio\n"
                + "Email: abc@gmail.com\n"
                + "Phone: 98761234\n"
                + "Task(s) Assigned: [Complete event poster]\n"
                + "Skills: [Java]\n", out.getOutputToUser());

    }
}
