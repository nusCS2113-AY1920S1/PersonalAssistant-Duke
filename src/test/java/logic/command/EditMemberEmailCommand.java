package logic.command;

import common.DukeException;
import logic.parser.edit.EditMemberEmailParser;
import model.Model;
import model.ModelController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EditMemberEmailCommand {
    @Test
    public void editMemberEmailCommand_inputNameNotInMemberList_throwsException() throws DukeException {
        Model model = new ModelController();
        model.getMemberList().clear();
        model.addMember("test1");
        Command command = EditMemberEmailParser.parseEditMemberEmail("test2 /to test@email.com");
        assertThrows(DukeException.class, () -> command.execute(model));
    }

    @Test
    public void editMemberPhoneCommand_wrongEmailFormat_throwsException() throws DukeException {
        Model model = new ModelController();
        model.getMemberList().clear();
        model.addMember("test");
        Command command = EditMemberEmailParser.parseEditMemberEmail("test /to test@email");
        assertThrows(DukeException.class, () -> command.execute(model));
    }

    @Test
    public void editMemberEmailCommand_set() throws DukeException {
        Model model = new ModelController();
        model.getMemberList().clear();
        model.addMember("test");
        Command command = EditMemberEmailParser.parseEditMemberEmail("test /to test@email.com");
        CommandOutput out = command.execute(model);
        model.getMemberList().clear();
        assertEquals("You have set the email of member: [test] to [[test@email.com]]", out.getOutputToUser());
    }

    @Test
    public void editMemberEmailCommand_noUpdate() throws DukeException {
        Model model = new ModelController();
        model.getMemberList().clear();
        model.addMember("test");
        Command command1 = EditMemberEmailParser.parseEditMemberEmail("test /to test@email.com");
        command1.execute(model);
        Command command2 = EditMemberEmailParser.parseEditMemberEmail("test /to test@email.com");
        CommandOutput out = command2.execute(model);
        model.getMemberList().clear();
        assertEquals("No update, they are the same.", out.getOutputToUser());
    }

    @Test
    public void editMemberEmailCommand_update() throws DukeException {
        Model model = new ModelController();
        model.getMemberList().clear();
        model.addMember("test");
        Command command1 = EditMemberEmailParser.parseEditMemberEmail("test /to test1@email.com");
        command1.execute(model);
        Command command2 = EditMemberEmailParser.parseEditMemberEmail("test /to test2@email.com");
        CommandOutput out = command2.execute(model);
        model.getMemberList().clear();
        assertEquals("You have update the email of member: "
                + "[test] from [[test1@email.com]] to [[test2@email.com]]", out.getOutputToUser());
    }
}
