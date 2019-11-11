package logic.parser;

import common.DukeException;
import logic.parser.delete.DeleteCommandParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FindCommandParserTest {

    @Test
    public void findCommand_keyword_empty() {
        assertThrows(DukeException.class, () ->
                FindCommandParser.parseFindCommand(""));
    }
}
