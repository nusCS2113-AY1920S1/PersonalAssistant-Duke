package oof;

import oof.command.CompleteCommand;
import oof.command.ExitCommand;
import oof.command.ListCommand;
import oof.command.AddToDoCommand;
import oof.command.AddDeadlineCommand;
import oof.command.AddEventCommand;
import oof.command.ScheduleCommand;
import oof.exception.OofException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class CommandParserTest {

    @Test
    public void testBye() throws OofException {
        assertTrue(CommandParser.parse("bye") instanceof ExitCommand);
    }

    @Test
    public void testList() throws OofException {
        assertTrue(CommandParser.parse("list") instanceof ListCommand);
    }

    @Test
    public void testDone() throws OofException {
        assertTrue(CommandParser.parse("done 2") instanceof CompleteCommand);
    }

    @Test
    public void testTodo() throws OofException {
        assertTrue(CommandParser.parse("todo borrow book") instanceof AddToDoCommand);
    }

    @Test
    public void testDeadline() throws OofException {
        assertTrue(CommandParser.parse("deadline homework /by 11-11-2019 11:11") instanceof AddDeadlineCommand);
    }

    @Test
    public void testEvent() throws OofException {
        assertTrue(CommandParser.parse("event testing /at 11-11-2019 11:11") instanceof AddEventCommand);
    }

    @Test
    public void testSchedule() throws OofException {
        assertTrue(CommandParser.parse("schedule 11-11-2019") instanceof ScheduleCommand);
    }

    @Test
    public void testInvalid() throws OofException {
        try {
            CommandParser.parse("abcd");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! I'm sorry, but I don't know what that means :-(", e.getMessage());
        }
    }
}
