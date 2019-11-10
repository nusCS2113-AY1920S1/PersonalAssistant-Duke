package duke.logic.parser.shortcut;

import duke.logic.parser.exceptions.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SetShortcutParserTest {
    private SetShortcutCommandParser parser = new SetShortcutCommandParser();

    @Test
    public void parse_valid_success() {
        //Empty command
        Assertions.assertAll(() -> parser.parse("[name]  [  ]"));

        //Single command
        Assertions.assertAll(() -> parser.parse("[name] [order add]"));

        //Multiple command
        Assertions.assertAll(() -> parser.parse("[name] [order add; order remove]"));
    }

    @Test
    public void parse_invalidArguments_throwsParseException() {
        //empty name
        Assertions.assertThrows(ParseException.class, () -> parser.parse("[] [order ad]d"));

        //blank name
        Assertions.assertThrows(ParseException.class, () -> parser.parse("[ \n \t \f ] [order ad]d"));

        //empty/blank commands
        Assertions.assertThrows(ParseException.class, () -> parser.parse("[test] [;order add]"));
        Assertions.assertThrows(ParseException.class, () -> parser.parse("[test] [;;order add]"));
        Assertions.assertThrows(ParseException.class, () -> parser.parse("[test] [;; \n \t \f]"));
        Assertions.assertThrows(ParseException.class, () -> parser.parse("[test] [order add;]"));
        Assertions.assertThrows(ParseException.class, () -> parser.parse("[test] [order add;;]"));
        Assertions.assertThrows(ParseException.class, () -> parser.parse("[test] [;]"));

    }
}
