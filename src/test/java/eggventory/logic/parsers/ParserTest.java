package eggventory.logic.parsers;

import eggventory.commons.exceptions.BadInputException;
import eggventory.commons.exceptions.InsufficientInfoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


//@@author cyanoei
class ParserTest {

    Parser testParser = new Parser();

    @Test
    public void isCommandComplete_CommandComplete_ReturnsTrue() {
        assertEquals(true, Parser.isCommandComplete("a b c", 2));
    }

    @Test
    public void isCommandComplete_CommandExtra_ReturnsTrue() {
        assertEquals(true, Parser.isCommandComplete("a b c optional", 2));
    }

    @Test
    public void isCommandComplete_CommandCompleteWithTrailingSpaces_ReturnsTrue() {
        assertEquals(true, Parser.isCommandComplete("a b c     ", 2));
    }

    @Test
    public void isCommandComplete_CommandIncomplete_ReturnsFalse() {
        assertEquals(true, Parser.isCommandComplete("a b c", 2));
        assertEquals(false, Parser.isCommandComplete("a b c", 3));

        assertEquals(true, Parser.isCommandComplete("a b c", 2));
        assertEquals(false, Parser.isCommandComplete("a", 1));
    }

    @Test
    public void checkIsInteger_IsInteger_Success() {
        assertDoesNotThrow(() -> Parser.isCheckIsInteger("0", "test"));
        assertDoesNotThrow(() -> Parser.isCheckIsInteger("-1", "test"));
        assertDoesNotThrow(() -> Parser.isCheckIsInteger("100", "test"));
    }

    @Test
    public void checkIsInteger_NotInteger_ThrowsBadInputException() {
        assertThrows(BadInputException.class, () -> Parser.isCheckIsInteger("string", "test"));
        assertThrows(BadInputException.class, () -> Parser.isCheckIsInteger("r500", "test"));
        assertThrows(BadInputException.class, () -> Parser.isCheckIsInteger("0.1", "test"));
        assertThrows(BadInputException.class, () -> Parser.isCheckIsInteger("///", "test"));
    }

    @Test
    public void checkIsIntegerOutput_StringInput_MessageOutput() {
        try {
            Parser.isCheckIsInteger("a", "quantity");
        } catch (BadInputException e) {
            assertEquals(e.getMessage(), "Sorry, the input for quantity has to be an integer!");
        }

        try {
            Parser.isCheckIsInteger("a", "minimum quantity");
        } catch (BadInputException e) {
            assertEquals(e.getMessage(), "Sorry, the input for minimum quantity has to be an integer!");
        }

    }

    @Test
    public void checkReservedInput_InputNotReserved_ReturnsFalse() {
        String[] testInputs = {"Rishab", "Raghav", "Rebecca", "Dana", "Yanbo", "Resistor", "Arduino", "Eggventory"};

        for (String input : testInputs) {
            assertEquals(false, Parser.isReserved(input));
        }
    }

    @Test
    public void checkReservedInput_InputIsReserved_ReturnsTrue() {
        for (String input : Parser.getReservedNames()) {
            assertEquals(true, Parser.isReserved(input));
        }
    }

    @Test
    public void parseInput_GoodInput_ReturnsCommandObject() throws Exception {
        assertNotNull(testParser.parse("help"));
        assertNotNull(testParser.parse("bye"));
    }

    @Test
    public void parseInput_IncompleteInput_ThrowsInsufficientInfoException() throws Exception {
        assertThrows(InsufficientInfoException.class, () -> testParser.parse("add"));
        assertThrows(InsufficientInfoException.class, () -> testParser.parse("delete"));
        assertThrows(InsufficientInfoException.class, () -> testParser.parse("list"));
        assertThrows(InsufficientInfoException.class, () -> testParser.parse("find"));
        assertThrows(InsufficientInfoException.class, () -> testParser.parse("edit"));
    }

    @Test
    public void parseInput_WrongInput_ThrowsBadInputException() throws Exception {
        assertThrows(BadInputException.class, () -> testParser.parse("hello"));
        assertThrows(BadInputException.class, () -> testParser.parse("loan"));
        assertThrows(BadInputException.class, () -> testParser.parse("return"));
        assertThrows(BadInputException.class, () -> testParser.parse("lost"));
    }


}