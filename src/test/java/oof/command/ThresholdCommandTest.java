package oof.command;

import oof.Oof;
import oof.exception.ParserException;
import oof.exception.command.CommandException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//@@author Kenlhc

/**
 * Testing Class for ThresholdCommand.
 */
public class ThresholdCommandTest {

    private Oof oof = new Oof();

    /**
     * Tests the behaviour when an invalid format is given.
     */
    @Test
    void parse_ThresholdEnteredWithWrongFormat_ThrowsException() {
        try {
            oof.executeCommand("threshold");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please enter a number!", e.getMessage());
        }
        try {
            oof.executeCommand("threshold 10:00");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please enter a valid number!", e.getMessage());
        }
        try {
            oof.executeCommand("threshold 48/2");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please enter a valid number!", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when a non integer is given.
     */
    @Test
    void parse_ThresholdEnteredWithNonInteger_ThrowsException() {
        try {
            oof.executeCommand("threshold aa");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please enter a valid number!", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when a negative integer is given.
     */
    @Test
    void parse_ThresholdEnteredWithNegativeInteger_ThrowsException() {
        try {
            oof.executeCommand("threshold -10");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("Threshold given invalid! Please input positive integers.", e.getMessage());
        }
    }

    /**
     * Tests the behaviour for updating threshold.
     *
     * @throws CommandException if command is invalid.
     * @throws ParserException  if command cannot be parsed.
     */
    @Test
    void execute_CorrectCommand_WriteThreshold() throws CommandException, ParserException {
        ByteArrayOutputStream actualMessagePrinted = new ByteArrayOutputStream();
        System.setOut(new PrintStream(actualMessagePrinted));
        oof.executeCommand("threshold 48");
        String expectedMessage = "________________________________________________________________________________"
                + System.lineSeparator() + " You will now be reminded of deadlines in 48 hours.";
        assertEquals(expectedMessage, actualMessagePrinted.toString().trim());
    }
}
