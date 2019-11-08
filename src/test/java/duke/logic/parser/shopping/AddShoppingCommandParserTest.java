package duke.logic.parser.shopping;

import duke.logic.parser.exceptions.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AddShoppingCommandParserTest {

    private AddShoppingCommandParser parser = new AddShoppingCommandParser();

    @Test
    public void parseValidInput_success() {
        Assertions.assertAll(() -> parser.parse("-name egg"));
        Assertions.assertAll(() -> parser.parse("-name egg -qty 5 -cost 3 -rmk in boxes"));
    }

    @Test
    public void parseValidQuantity_success() {
        Assertions.assertAll(() -> parser.parse("-name egg -qty 1000"));
        Assertions.assertAll(() -> parser.parse("-name bread -qty 50000"));
        Assertions.assertAll(() -> parser.parse("-name bread -qty 49999"));
        Assertions.assertAll(() -> parser.parse("-name fish -qty 0"));
        Assertions.assertAll(() -> parser.parse("-name fish -qty 1"));
    }

    @Test
    public void parseInvalidQuantity_throwsParseException() {
        Assertions.assertThrows(ParseException.class, () -> parser.parse("-name egg -qty 50001"));
        Assertions.assertThrows(ParseException.class, () -> parser.parse("-name egg -qty"));
        Assertions.assertThrows(ParseException.class, () -> parser.parse("-name egg -qty -1"));
    }

    @Test
    public void parseNoName_throwsIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> parser.parse("-qty 100"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> parser.parse("-qty 100 -name"));
    }

    @Test
    public void parseInvalidName_throwsParseException() {
        Assertions.assertThrows(ParseException.class, () -> parser.parse("-name Coca-Cola -qty 100"));
    }

    @Test
    public void parseValidCost_success() {
        Assertions.assertAll(() -> parser.parse("-name egg -cost 1000"));
        Assertions.assertAll(() -> parser.parse("-name bread -cost 0"));
        Assertions.assertAll(() -> parser.parse("-name bread -cost 1"));
    }

    @Test
    public void parseInvalidCost_throwsParseException() {
        Assertions.assertThrows(ParseException.class, () -> parser.parse("-name milk -cost -1.00"));
        Assertions.assertThrows(ParseException.class, () -> parser.parse("-name milk -cost"));
        Assertions.assertThrows(ParseException.class, () -> parser.parse("-name milk -cost $5"));
    }

    @Test
    public void parseInvalidRemarks_throwsParseException() {
        Assertions.assertThrows(ParseException.class, () -> parser.parse("-name milk -rmk -in boxes"));
    }
}