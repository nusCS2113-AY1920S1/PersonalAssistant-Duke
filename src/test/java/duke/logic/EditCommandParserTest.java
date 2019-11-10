package duke.logic;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Optional;

import duke.exception.DukeException;
import duke.logic.parser.EditCommandParser;
import duke.logic.parser.KeywordAndField;
import org.junit.jupiter.api.Test;

/**
 * Contains tests to tests for exception thrown when parameters parsed in is of incorrect format
 */
class EditCommandParserTest {

    @Test
    public void getIndexFromCommand_nonNumericalIndex_failure() {
        EditCommandParser editCommandParser = new EditCommandParser();
        ArrayList<KeywordAndField> keywordAndFields = new ArrayList<KeywordAndField>();
        assertThrows(DukeException.class, () -> {
            editCommandParser.parse(Optional.of("cs"), "he -description muji pen -r weekly");
        });
    }

    @Test
    public void getKeywordAndField_noKeyword_failure() {
        EditCommandParser editCommandParser = new EditCommandParser();
        assertThrows(DukeException.class, () -> {
            editCommandParser.parse(Optional.of("cs"), "");
        });
    }
}
