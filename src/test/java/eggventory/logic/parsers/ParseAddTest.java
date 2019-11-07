package eggventory.logic.parsers;

import eggventory.commons.exceptions.BadInputException;
import eggventory.commons.exceptions.InsufficientInfoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author patwaririshab
/**
 * Tests for correct throw of exceptions given insufficient or bad input.
 * For the sake of the tests below, string arguments passed to the test are
 * represented by 'arg[no]' while integer arguments are represented by 500.
 */
class ParseAddTest {
    ParseAdd testParser = new ParseAdd();

    @Test
    public void testParse_ValidInput_Succeeds() {
        //stock, requires 4 arguments
        assertDoesNotThrow(() -> testParser.parse("stock arg1 arg2 500 arg4"));

        //stocktype, requires 1 argument
        assertDoesNotThrow(() -> testParser.parse("stocktype arg1"));

        //loan, requires 3 arguments
        assertDoesNotThrow(() -> testParser.parse("loan arg1 arg2 500"));

        //person, requires 2 arguments
        assertDoesNotThrow(() -> testParser.parse("person arg1 arg2"));
    }


    @Test
    public void testParse_IncompleteInputString_ThrowsInsufficientInfoException()  {
        //stock, required 4 arguments, provided 3
        assertThrows(InsufficientInfoException.class, () -> testParser.parse("stock arg1 arg2 500"));

        //stocktype, required 1 argument, provided 0
        assertThrows(InsufficientInfoException.class, () -> testParser.parse("stocktype"));

        //loan, required 3 arguments, provided 2
        assertThrows(InsufficientInfoException.class, () -> testParser.parse("loan arg1 arg2"));

        //person, required 2 arguments, provided 1
        assertThrows(InsufficientInfoException.class, () -> testParser.parse("person arg1"));
    }

    @Test
    public void testParse_BadFirstInput_ThrowsBadInputException() {
        //Input feature can only be stock, stocktype, loan or person
        //string arguments represented by <arg<no>> and int arguments represented by 500
        assertDoesNotThrow(() -> testParser.parse("stock arg1 arg2 500 arg4"));
        assertDoesNotThrow(() -> testParser.parse("stocktype arg1"));
        assertDoesNotThrow(() -> testParser.parse("loan arg1 arg2 500"));
        assertDoesNotThrow(() -> testParser.parse("person arg1 arg2"));
        BadInputException exception = assertThrows(BadInputException.class,
            () -> testParser.parse("random something arg 1"));
        assertEquals("Unexpected value: random", exception.getMessage());
    }

    @Test
    public void testParseAddStockType_ReservedArgument_ThrowsBadInputException() {
        assertDoesNotThrow(() -> testParser.parse("stocktype arg1"));
        BadInputException exception =  assertThrows(BadInputException.class,
            () -> testParser.parse("stocktype all"));

        assertEquals("'all' is an invalid name as it is a keyword for an existing command.",
                exception.getMessage());
    }

    @Test
    public void testParseAddPerson_ReservedArgument_ThrowsBadInputException() {
        assertDoesNotThrow(() -> testParser.parse("person arg1 arg2"));
        BadInputException exception =  assertThrows(BadInputException.class,
            () -> testParser.parse("person all all2"));

        assertEquals("'all' is an invalid name as it is a keyword for an existing command.",
                exception.getMessage());
    }
    //@@author
}