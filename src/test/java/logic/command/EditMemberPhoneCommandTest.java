package logic.command;

import common.DukeException;
import logic.parser.edit.EditMemberPhoneParser;
import model.Model;
import model.ModelController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EditMemberPhoneCommandTest {
    
    @Test
    public void editMemberPhoneCommand_inputNameNotInMemberList_throwsException() throws DukeException {
        Model model = new ModelController();
        model.getMemberList().clear();
        model.addMember("test1");
        Command command = EditMemberPhoneParser.parseEditMemberPhone("test2 /to 11112222");
        assertThrows(DukeException.class, () -> command.execute(model));
    }

    @Test
    public void editMemberPhoneCommand_wrongPhoneFormat_throwsException() throws DukeException {
        Model model = new ModelController();
        model.getMemberList().clear();
        model.addMember("test");
        Command command = EditMemberPhoneParser.parseEditMemberPhone("test /to 111122a2");
        assertThrows(DukeException.class, () -> command.execute(model));
    }

    @Test
    public void editMemberPhoneCommand_set() throws DukeException {
        Model model = new ModelController();
        model.getMemberList().clear();
        model.addMember("test");
        Command command = EditMemberPhoneParser.parseEditMemberPhone("test /to 11112222");
        CommandOutput out = command.execute(model);
        assertEquals("You have set the phone of member: [test] to [[11112222]]", out.getOutputToUser());
    }

    @Test
    public void editMemberPhoneCommand_noUpdate() throws DukeException {
        Model model = new ModelController();
        model.getMemberList().clear();
        model.addMember("test");
        Command command1 = EditMemberPhoneParser.parseEditMemberPhone("test /to 11112222");
        command1.execute(model);
        Command command2 = EditMemberPhoneParser.parseEditMemberPhone("test /to 11112222");
        CommandOutput out = command2.execute(model);
        assertEquals("No update, they are the same.", out.getOutputToUser());
    }

    @Test
    public void editMemberPhoneCommand_update() throws DukeException {
        Model model = new ModelController();
        model.getMemberList().clear();
        model.addMember("test");
        Command command1 = EditMemberPhoneParser.parseEditMemberPhone("test /to 11112222");
        command1.execute(model);
        Command command2 = EditMemberPhoneParser.parseEditMemberPhone("test /to 22223333");
        CommandOutput out = command2.execute(model);
        assertEquals("You have update the phone of member: "
                + "[test] from [[11112222]] to [[22223333]]", out.getOutputToUser());
    }

}
