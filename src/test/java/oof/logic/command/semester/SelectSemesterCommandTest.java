package oof.logic.command.semester;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Test;

import oof.Oof;
import oof.commons.exceptions.ParserException;
import oof.commons.exceptions.command.CommandException;

public class SelectSemesterCommandTest {

    @Test
    public void execute_InvalidIndexArgument_ExceptionThrown() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            new Oof().executeCommand("semester /select -1");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!! The index is invalid.", e.getMessage());
        }
    }
}
