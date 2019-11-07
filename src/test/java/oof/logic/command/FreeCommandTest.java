package oof.logic.command;

import oof.Oof;
import oof.commons.exceptions.ParserException;
import oof.commons.exceptions.command.CommandException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//@@author Kenlhc

/**
 * Testing Class for FreeCommand.
 */
public class FreeCommandTest {

    private Oof oof = new Oof();

    /**
     * Tests the behaviour when an invalid date format is given.
     */
    @Test
    void parse_FreeEnteredWithWrongDateFormat_ThrowsException() {
        try {
            oof.executeCommand("free");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please enter the date in the following format: DD-MM-YYYY", e.getMessage());
        }
        try {
            oof.executeCommand("free 08/11/2019");
            fail();
        } catch (CommandException | ParserException  e) {
            assertEquals("OOPS!!! Please enter the date in the following format: DD-MM-YYYY", e.getMessage());
        }
        try {
            oof.executeCommand("free 08 11 2019");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please enter the date in the following format: DD-MM-YYYY", e.getMessage());
        }
        try {
            oof.executeCommand("free 2019-11-08");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please enter either today's date or a date in the future!", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when a non integer date is given.
     */
    @Test
    void parse_FreeEnteredWithNonIntegerAsDate_ThrowsException() {
        try {
            oof.executeCommand("free abc");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please enter the date in the following format: DD-MM-YYYY", e.getMessage());
        }
        try {
            oof.executeCommand("free aa-bb-cc");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please enter the date in the following format: DD-MM-YYYY", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when negative integer dates are given.
     */
    @Test
    void parse_FreeEnteredWithNegativeIntegersInDate_ThrowsException() {
        try {
            oof.executeCommand("free -1-11-2019");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please enter either today's date or a date in the future!", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when dates before the current dates are given.
     */
    @Test
    void parse_FreeEnteredWithDateBeforeCurrentDate_ThrowsException() {
        try {
            oof.executeCommand("free 01-10-2019");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please enter either today's date or a date in the future!", e.getMessage());
        }
    }
}