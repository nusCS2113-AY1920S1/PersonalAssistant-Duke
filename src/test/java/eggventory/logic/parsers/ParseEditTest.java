package eggventory.logic.parsers;

import eggventory.commons.exceptions.BadInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

//@@author patwaririshab
class ParseEditTest {
    ParseEdit testParser = new ParseEdit();

    @Test
    public void setParseEditStockType_ReservedArgument_ThrowsBadInputException() {
        assertDoesNotThrow(() -> testParser.parse("stocktype arg1 arg2"));
        assertThrows(BadInputException.class, () -> testParser.parse("stocktype arg1 all"));
        assertThrows(BadInputException.class, () -> testParser.parse("stocktype all arg2"));
    }

    @Test
    public void setParseEditStock_InvalidProperty_ThrowsBadInputException() {
        //stockcode
        assertDoesNotThrow(() -> testParser.parse("stock #stockcode stockcode arg3"));
        assertThrows(BadInputException.class, () -> testParser.parse("stock #stockcode wrongProperty arg3"));

        //quantity
        assertDoesNotThrow(() -> testParser.parse("stock #stockcode quantity 500"));
        assertThrows(BadInputException.class, () -> testParser.parse("stock #stockcode wrongProperty 500"));

        //loaned
        assertDoesNotThrow(() -> testParser.parse("stock #stockcode loaned 500"));
        assertThrows(BadInputException.class, () -> testParser.parse("stock #stockcode wrongProperty 500"));

        //lost
        assertDoesNotThrow(() -> testParser.parse("stock #stockcode lost 500"));
        assertThrows(BadInputException.class, () -> testParser.parse("stock #stockcode wrongProperty 500"));

        //description
        assertDoesNotThrow(() -> testParser.parse("stock #stockcode description test"));
        assertThrows(BadInputException.class, () -> testParser.parse("stock #stockcode wrongProperty test"));

        //minimum
        assertDoesNotThrow(() -> testParser.parse("stock #stockcode minimum 500"));
        assertThrows(BadInputException.class, () -> testParser.parse("stock #stockcode wrongProperty 500"));
    }

    @Test
    public void testParseEdit_BadFirstInput_ThrowsBadInputException() {
        //only stock and stocktype can be first input
        //edit stock <stockcode> <property> <newValue>
        assertDoesNotThrow(() -> testParser.parse("stock #test stockcode arg3"));
        //edit stocktype <stocktypename> <newName>
        assertDoesNotThrow(() -> testParser.parse("stocktype arg1 arg2"));
        assertThrows(BadInputException.class, () -> testParser.parse("sommething arg1 arg2"));
    }
    //@@author
}