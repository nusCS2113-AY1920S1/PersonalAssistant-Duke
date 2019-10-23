package duke.logic.parser.shortcut;

import duke.logic.parser.exceptions.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SetShortcutParserTest {
    private SetShortcutCommandParser parser = new SetShortcutCommandParser();

    @Test
    public void createShortcut_emptyName_failure() {
        Assertions.assertThrows(ParseException.class, () -> parser.parse(" order add"));
    }

    @Test
    public void createShortcut_emptyUserInputs_success() {
        Assertions.assertAll(() -> parser.parse("name"));
    }

    @Test
    public void createShortcut_singleUserInput_success() {
        Assertions.assertAll(() -> parser.parse("name order add"));
    }

    @Test
    public void createShortcut_multipleUserInput_success() {
        Assertions.assertAll(() -> parser.parse("name order add; order remove"));
    }
}
