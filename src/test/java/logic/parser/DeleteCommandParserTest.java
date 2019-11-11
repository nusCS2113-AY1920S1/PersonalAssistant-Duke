package logic.parser;

import common.DukeException;
import logic.parser.delete.DeleteCommandParser;
import logic.parser.delete.DeleteTaskParser;
import logic.parser.edit.EditMemberBioParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeleteCommandParserTest {

    @Test
    public void deleteCommand_noType() {
        assertThrows(DukeException.class, () ->
                DeleteCommandParser.parseDeleteCommand("something"));
    }

    @Test
    public void deleteCommand_member_noName() {
        assertThrows(DukeException.class, () ->
                DeleteCommandParser.parseDeleteCommand(""));
    }

    @Test
    public void deleteCommand_task_noIndex() {
        assertThrows(DukeException.class, () ->
                DeleteTaskParser.parseDeleteTask(" "));
    }
}
