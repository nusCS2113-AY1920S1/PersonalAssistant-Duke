package duke.logic.parser.shopping;

import duke.logic.parser.exceptions.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BuyShoppingCommandParserTest {

    private BuyShoppingCommandParser parser = new BuyShoppingCommandParser();

    @Test
    public void parseValidIndices_success() {
        Assertions.assertAll(() -> parser.parse("1"));
        Assertions.assertAll(() -> parser.parse("1,2"));
        Assertions.assertAll(() -> parser.parse("1~10"));
    }

    @Test
    public void parseInvalidIndices_throwsParseException() {
        Assertions.assertThrows(ParseException.class, () -> parser.parse("0"));
        Assertions.assertThrows(ParseException.class, () -> parser.parse("0,1"));
        Assertions.assertThrows(ParseException.class, () -> parser.parse("0~5"));
        Assertions.assertThrows(ParseException.class, () -> parser.parse("5~3"));
        Assertions.assertThrows(ParseException.class, () -> parser.parse(""));
        Assertions.assertThrows(ParseException.class, () -> parser.parse("Cheese"));
    }

}
