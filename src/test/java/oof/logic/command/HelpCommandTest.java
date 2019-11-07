package oof.logic.command;

//@@author debbiextan

import oof.Oof;
import oof.commons.exceptions.ParserException;
import oof.commons.exceptions.command.CommandException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;


public class HelpCommandTest {

    private Oof oof = new Oof();

    /**
     * Tests behavior when keyword is invalid.
     */
    @Test
    public void execute_invalidKeyword_exceptionThrown() {
        try {
            oof.executeCommand("help test");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("Invalid keyword!", e.getMessage());
        }
    }
}
