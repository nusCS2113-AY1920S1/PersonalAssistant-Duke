package logic.parser.edit;

import common.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EditMemberOtherParserTest {

    @Test
    public void editTypeNotExit() throws DukeException {
        assertThrows(DukeException.class, () ->
                EditMemberParser.parseEditMember("something"));
    }
}
