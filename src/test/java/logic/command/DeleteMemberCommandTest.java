package logic.command;

import common.DukeException;
import logic.parser.delete.DeleteMemberParser;
import model.Model;
import model.ModelController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author yuyanglin28

public class DeleteMemberCommandTest {

    @Test
    public void deleteMemberCommand_memberNotInList() throws DukeException {
        Model model = new ModelController();
        model.getMemberList().clear();
        model.addMember("test1");
        Command command = DeleteMemberParser.parseDeleteMember("test");
        CommandOutput out = command.execute(model);
        assertEquals("test is not in the member list.\n", out.getOutputToUser());
        model.getMemberList().clear();
        model.save();
    }

    @Test
    public void deleteMemberCommand_success() throws DukeException {
        Model model = new ModelController();
        model.getMemberList().clear();
        model.addMember("test1");
        Command command = DeleteMemberParser.parseDeleteMember("test1");
        CommandOutput out = command.execute(model);
        assertEquals("You have removed a member: test1\n", out.getOutputToUser());
    }
}
