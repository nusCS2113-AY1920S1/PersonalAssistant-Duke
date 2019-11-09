package eggventory.logic.parsers;

import eggventory.commons.exceptions.InsufficientInfoException;
import eggventory.commons.exceptions.BadInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

//@@author yanprosobo
class ParseFindTest {
    ParseFind testParser = new ParseFind();

    //add to this test when adding more options for find
    @Test
    public void testParseFind_BadFirstInput_ThrowsBadInputException() {
        assertDoesNotThrow(() -> testParser.parse("description query"));
        assertDoesNotThrow(() -> testParser.parse("description search"));
        assertThrows(BadInputException.class, () -> testParser.parse("stock multiple query"));
        assertThrows(BadInputException.class, () -> testParser.parse("stock"));
    }

    //Testing for the requirement of <query> after "find description"
    @Test
    public void testParseFind_RequiredSearchQuery_ThrowsInsufficientInfoException() {
        assertDoesNotThrow(() -> testParser.parse("description query"));
        assertDoesNotThrow(() -> testParser.parse("description search"));
        assertThrows(InsufficientInfoException.class, () -> testParser.parse("description"));
        assertThrows(InsufficientInfoException.class, () -> testParser.parse("description "));
        assertThrows(InsufficientInfoException.class, () -> testParser.parse("description \n"));
    }


}