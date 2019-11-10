package logic.parser.edit;

import common.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author JasonChanWQ

public class EditMemberNameParserTest {

    @Test
    public void editMemberNameParser_emptyInput_throwsException() {
        assertThrows(DukeException.class, () ->
                EditMemberNameParser.parseEditMemberName(""));
    }

    @Test
    public void editMemberNameParser_emptyOldName_throwsException() {
        assertThrows(DukeException.class, () ->
                EditMemberNameParser.parseEditMemberName(" /to New Name"));
    }

    @Test
    public void editMemberNameParser_emptyNewName_throwsException() {
        assertThrows(DukeException.class, () ->
                EditMemberNameParser.parseEditMemberName("Old Name /to "));
    }

    @Test
    public void editMemberNameParser_toNotFound_throwsException() {
        assertThrows(DukeException.class, () ->
                EditMemberNameParser.parseEditMemberName("Old Name  New Name"));
    }
}
