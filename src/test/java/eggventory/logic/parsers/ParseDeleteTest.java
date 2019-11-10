package eggventory.logic.parsers;

import eggventory.commons.exceptions.BadInputException;
import eggventory.commons.exceptions.InsufficientInfoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author cyanoei
/**
 * Tests for correct throw of exceptions given insufficient or bad input.
 */
class ParseDeleteTest {
    ParseDelete testParser = new ParseDelete();

    @Test
    public void testParse_ValidInput_Success() {
        //stock, requires 1 arguments
        assertDoesNotThrow(() -> testParser.parse("stock abc"));

        //stocktype, requires 1 argument
        assertDoesNotThrow(() -> testParser.parse("stocktype abc"));

        //loan, requires 2 arguments
        assertDoesNotThrow(() -> testParser.parse("loan matric stockcode"));

        //person, requires 1 argument
        assertDoesNotThrow(() -> testParser.parse("person matric"));

        //template, requires 1 argument
        assertDoesNotThrow(() -> testParser.parse("template name"));
    }


    @Test
    public void testParse_IncompleteInputString_ThrowsInsufficientInfoException()  {
        //stock, requires 1
        assertThrows(InsufficientInfoException.class, () -> testParser.parse("stock"));

        //stocktype, requires 1 argument
        assertThrows(InsufficientInfoException.class, () -> testParser.parse("stocktype"));

        //loan, requires 2 arguments
        assertThrows(InsufficientInfoException.class, () -> testParser.parse("loan"));
        assertThrows(InsufficientInfoException.class, () -> testParser.parse("loan matric"));

        //person, requires 1 argument
        assertThrows(InsufficientInfoException.class, () -> testParser.parse("person"));

        //template, requires 1 argument
        assertThrows(InsufficientInfoException.class, () -> testParser.parse("template"));
    }

    @Test
    public void testParse_BadFirstInput_ThrowsBadInputException() {
        //Input feature can only be stock, stocktype, loan, person, template
        assertDoesNotThrow(() -> testParser.parse("stock abc"));
        assertDoesNotThrow(() -> testParser.parse("stocktype abc"));
        assertDoesNotThrow(() -> testParser.parse("loan matric stock"));
        assertDoesNotThrow(() -> testParser.parse("person name"));
        assertDoesNotThrow(() -> testParser.parse("template name"));

        assertThrows(BadInputException.class, () -> testParser.parse("string abc"));
    }

}