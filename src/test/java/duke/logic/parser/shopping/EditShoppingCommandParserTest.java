package duke.logic.parser.shopping;

import duke.logic.parser.exceptions.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EditShoppingCommandParserTest {

    private EditShoppingCommandParser parser = new EditShoppingCommandParser();

    @Test
    public void parseValidInput_success() {
        Assertions.assertAll(() -> parser.parse("5 -name egg"));
        Assertions.assertAll(() -> parser.parse("10 -name egg -qty 5 -cost 3 -rmk in boxes"));
    }

    @Test
    public void parseValidIndex_success() {
        Assertions.assertAll(() -> parser.parse("1"));
        Assertions.assertAll(() -> parser.parse("10"));
    }

    @Test
    public void parseInvalidIndex_throwsParseException() {
        Assertions.assertThrows(ParseException.class, () -> parser.parse("0"));
        Assertions.assertThrows(ParseException.class, () -> parser.parse(""));
        Assertions.assertThrows(ParseException.class, () -> parser.parse("Cheese"));
    }

    @Test
    public void parseValidQuantity_success() {
        Assertions.assertAll(() -> parser.parse("1 -qty 1000"));
        Assertions.assertAll(() -> parser.parse("1 -qty 50000"));
        Assertions.assertAll(() -> parser.parse("1 -qty 49999"));
        Assertions.assertAll(() -> parser.parse("1 -qty 0"));
        Assertions.assertAll(() -> parser.parse("1 -qty 1"));
    }

    @Test
    public void parseInvalidQuantity_throwsParseException() {
        Assertions.assertThrows(ParseException.class, () -> parser.parse("1 -qty"));
        Assertions.assertThrows(ParseException.class, () -> parser.parse("1 -qty -1"));
    }

    @Test
    public void parseInvalidName_throwsParseException() {
        Assertions.assertThrows(ParseException.class, () -> parser.parse("1 -name Coca-Cola"));
    }

    @Test
    public void parseValidCost_success() {
        Assertions.assertAll(() -> parser.parse("1 -cost 1000"));
        Assertions.assertAll(() -> parser.parse("1 -cost 0"));
        Assertions.assertAll(() -> parser.parse("1 -cost 1"));
    }

    @Test
    public void parseInvalidCost_throwsParseException() {
        Assertions.assertThrows(ParseException.class, () -> parser.parse("1 -cost -1.00"));
        //Assertions.assertThrows(ParseException.class, () -> parser.parse("1 -cost"));
        Assertions.assertThrows(ParseException.class, () -> parser.parse("1 -cost $5"));
    }

    @Test
    public void parseInvalidRemarks_throwsParseException() {
        Assertions.assertThrows(ParseException.class, () -> parser.parse("1 -rmk -in boxes"));
    }
}
