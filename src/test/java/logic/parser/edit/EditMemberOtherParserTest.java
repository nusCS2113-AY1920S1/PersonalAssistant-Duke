package logic.parser.edit;

import common.DukeException;
import logic.command.EditMemberEmailCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EditMemberOtherParserTest {

    @Test
    public void editTypeNotExit() {
        assertThrows(DukeException.class, () ->
                EditMemberParser.parseEditMember("something"));
    }

    @Test
    public void editMemberEmail_name_empty() {
        assertThrows(DukeException.class, () ->
                EditMemberEmailParser.parseEditMemberEmail(" /to aa@aa.com"));
    }

    @Test
    public void editMemberEmail_content_empty() {
        assertThrows(DukeException.class, () ->
                EditMemberEmailParser.parseEditMemberEmail("member name /to"));
    }

    @Test
    public void editMemberPhone_name_empty() {
        assertThrows(DukeException.class, () ->
                EditMemberPhoneParser.parseEditMemberPhone("/to 11111"));
    }

    @Test
    public void editMemberPhone_content_empty() {
        assertThrows(DukeException.class, () ->
                EditMemberPhoneParser.parseEditMemberPhone("member name /to"));
    }

    @Test
    public void editMemberBio_name_empty() {
        assertThrows(DukeException.class, () ->
                EditMemberBioParser.parseEditMemberBio("/to bio"));
    }

    @Test
    public void editMemberBio_content_empty() {
        assertThrows(DukeException.class, () ->
                EditMemberBioParser.parseEditMemberBio("member name /to"));
    }
}
