package logic.command;

import common.DukeException;
import logic.parser.edit.EditMemberBioParser;
import model.Model;
import model.ModelController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author yuyanglin28

public class EditMemberBioCommandTest {
    @Test
    public void editMemberBioCommand_inputNameNotInMemberList_throwsException() throws DukeException {
        Model model = new ModelController();
        model.getMemberList().clear();
        model.addMember("test1");
        Command command = EditMemberBioParser.parseEditMemberBio("test2 /to test bio");
        assertThrows(DukeException.class, () -> command.execute(model));
    }

    @Test
    public void editMemberBioCommand_set() throws DukeException {
        Model model = new ModelController();
        model.getMemberList().clear();
        model.addMember("test");
        Command command = EditMemberBioParser.parseEditMemberBio("test /to test bio");
        CommandOutput out = command.execute(model);
        assertEquals("You have set the bio of member: [test] to [[test bio]]", out.getOutputToUser());
        model.getMemberList().clear();
        model.save();
    }

    @Test
    public void editMemberBioCommand_noUpdate() throws DukeException {
        Model model = new ModelController();
        model.getMemberList().clear();
        model.addMember("test");
        Command command1 = EditMemberBioParser.parseEditMemberBio("test /to test bio");
        command1.execute(model);
        Command command2 = EditMemberBioParser.parseEditMemberBio("test /to test bio");
        CommandOutput out = command2.execute(model);
        assertEquals("No update, they are the same.", out.getOutputToUser());
        model.getMemberList().clear();
        model.save();
    }

    @Test
    public void editMemberBioCommand_update() throws DukeException {
        Model model = new ModelController();
        model.getMemberList().clear();
        model.addMember("test");
        Command command1 = EditMemberBioParser.parseEditMemberBio("test /to test bio1");
        command1.execute(model);
        Command command2 = EditMemberBioParser.parseEditMemberBio("test /to test bio2");
        CommandOutput out = command2.execute(model);
        assertEquals("You have update the bio of member: "
                + "[test] from [[test bio1]] to [[test bio2]]", out.getOutputToUser());
        model.getMemberList().clear();
        model.save();
    }
}
