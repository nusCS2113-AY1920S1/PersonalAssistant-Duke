package oof;

import oof.command.AddDeadlineCommand;
import oof.command.AddEventCommand;
import oof.command.AddToDoCommand;
import oof.command.CalendarCommand;
import oof.command.CompleteCommand;
import oof.command.DeleteCommand;
import oof.command.ExitCommand;
import oof.command.FindCommand;
import oof.command.FreeCommand;
import oof.command.HelpCommand;
import oof.command.ListCommand;
import oof.command.RecurringCommand;
import oof.command.ScheduleCommand;
import oof.command.SnoozeCommand;
import oof.exception.OofException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class CommandParserTest {

    /**
     * Tests the command for exiting Oof.
     * @throws OofException Parse method throws OofException.
     */
    @Test
    public void parse_ByeEntered_ExitOof() throws OofException {
        assertTrue(CommandParser.parse("bye") instanceof ExitCommand);
    }

    /**
     * Tests the command for listing tasks.
     * @throws OofException Parse method throws OofException.
     */
    @Test
    public void parse_ListEntered_ListTasks() throws OofException {
        assertTrue(CommandParser.parse("list") instanceof ListCommand);
    }

    /**
     * Tests the command for printing command usage.
     * @throws OofException Parse method throws OofException.
     */
    @Test
    public void parse_HelpEntered_PrintCommandUsage() throws OofException {
        assertTrue(CommandParser.parse("help") instanceof HelpCommand);
    }

    /**
     * Tests the command for marking a task as done.
     * @throws OofException Parse method throws OofException.
     */
    @Test
    public void parse_DoneEnteredWithValidIndex_MarksTaskAsCompleted() throws OofException {
        assertTrue(CommandParser.parse("done 2") instanceof CompleteCommand);
    }

    /**
     * Tests the command for marking a task as done.
     */
    @Test
    public void parse_DoneEnteredWithoutIndex_ThrowOofException() {
        try {
            CommandParser.parse("done");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please enter a number!", e.getMessage());
        }
    }

    /**
     * Tests the command for marking a task as done.
     */
    @Test
    public void parse_DoneEnteredWithNonInteger_ThrowOofException() {
        try {
            CommandParser.parse("done a");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please enter a valid number!", e.getMessage());
        }
    }

    /**
     * Tests the command for adding a todo task.
     * @throws OofException Parse method throws OofException.
     */
    @Test
    public void parse_TodoEnteredWithCorrectFields_AddsTodoTask() throws OofException {
        assertTrue(CommandParser.parse("todo borrow book") instanceof AddToDoCommand);
    }

    /**
     * Tests the command for adding a deadline.
     * @throws OofException Parse method throws OofException.
     */
    @Test
    public void parse_DeadlineEnteredWithCorrectFields_AddsDeadlineTask() throws OofException {
        assertTrue(CommandParser.parse("deadline homework /by 11-11-2019 11:11") instanceof AddDeadlineCommand);
    }

    /**
     * Tests the command for adding an event.
     * @throws OofException Parse method throws OofException.
     */
    @Test
    public void parse_EventEnteredWithCorrectFields_AddsEventTask() throws OofException {
        assertTrue(CommandParser.parse("event testing /at 11-11-2019 11:11") instanceof AddEventCommand);
    }

    /**
     * Tests the command for deleting a task.
     * @throws OofException Parse method throws OofException.
     */
    @Test
    public void parse_DeleteEnteredWithValidIndex_DeletesTask() throws OofException {
        assertTrue(CommandParser.parse("delete 1") instanceof DeleteCommand);
    }

    /**
     * Tests the command for deleting a task.
     */
    @Test
    public void parse_DeleteEnteredWithoutIndex_ThrowsOofException() {
        try {
            CommandParser.parse("delete");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please enter a number!", e.getMessage());
        }
    }

    /**
     * Tests the command for deleting a task.
     */
    @Test
    public void parse_DeleteEnteredWithNonInteger_ThrowsOofException() {
        try {
            CommandParser.parse("delete a");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please enter a valid number!", e.getMessage());
        }
    }

    /**
     * Tests the command for finding a task.
     * @throws OofException Parse method throws OofException.
     */
    @Test
    public void parse_FindEntered_FindTasks() throws OofException {
        assertTrue(CommandParser.parse("find") instanceof FindCommand);
    }

    /**
     * Tests the command for snoozing a task.
     * @throws OofException Parse method throws OofException.
     */
    @Test
    public void parse_SnoozeEnteredWithValidIndex_SnoozesTask() throws OofException {
        assertTrue(CommandParser.parse("snooze 1") instanceof SnoozeCommand);
    }

    /**
     * Tests the command for snoozing a task.
     */
    @Test
    public void parse_SnoozeEnteredWithoutIndex_ThrowsOofException() throws OofException {
        try {
            CommandParser.parse("snooze");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please enter a number!", e.getMessage());
        }
    }

    /**
     * Tests the command for snoozing a task.
     */
    @Test
    public void parse_SnoozeEnteredWithNonInteger_ThrowsOofException() throws OofException {
        try {
            CommandParser.parse("snooze a");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please enter a valid number!", e.getMessage());
        }
    }

    /**
     * Tests the command for scheduling a task.
     * @throws OofException Parse method throws OofException.
     */
    @Test
    public void parse_ScheduleEntered_SchedulesTask() throws OofException {
        assertTrue(CommandParser.parse("schedule 11-11-2019") instanceof ScheduleCommand);
    }

    /**
     * Tests the command for recurring a task.
     * @throws OofException Parse method throws OofException.
     */
    @Test
    public void parse_RecurringEnteredWithValidParameters() throws OofException {
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
        assertTrue(CommandParser.parse("recurring 1 1") instanceof RecurringCommand);
    }

    /**
     * Tests the command for recurring a task.
     */
    @Test
    public void parse_RecurringEnteredWithoutOtherParameters_RecursTask() {
        try {
            CommandParser.parse("recurring");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please enter the task number and number of recurrences!", e.getMessage());
        }
    }

    /**
     * Tests the command for recurring a task.
     */
    @Test
    public void parse_RecurringEnteredWithoutNumberOfRecurrences_RecursTask() {
        try {
            CommandParser.parse("recurring 1");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please enter the number of recurrences!", e.getMessage());
        }
    }

    /**
     * Tests the command for recurring a task.
     */
    @Test
    public void parse_RecurringEnteredWithNonIntegers_RecursTask() {
        try {
            CommandParser.parse("recurring a a");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please enter valid numbers!", e.getMessage());
        }
    }

    /**
     * Tests the command for recurring a task.
     */
    @Test
    public void parse_RecurringEnteredWithInvalidFrequency_ThrowsOofException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("a".getBytes());
            System.setIn(in);
            CommandParser.parse("recurring 1 1");
        } catch (OofException e) {
            assertEquals("OOPS!!! Please enter a valid number!", e.getMessage());
        }
    }

    /**
     * Tests the command for showing the calendar.
     * @throws OofException Parse method throws OofException.
     */
    @Test
    public void parse_CalendarEntered_ShowCalendar() throws OofException {
        assertTrue(CommandParser.parse("calendar") instanceof CalendarCommand);
    }

    /**
     * Tests the command to show free time slots.
     * @throws OofException Parse method throws OofException.
     */
    @Test
    public void parse_FreeEnteredWithCorrectFields_ShowFreeTimeSlots() throws OofException {
        assertTrue(CommandParser.parse("free 30-10-2019 23:59") instanceof FreeCommand);
    }

    /**
     * Tests invalid command.
     */
    @Test
    public void testInvalid() {
        try {
            CommandParser.parse("abcd");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! I'm sorry, but I don't know what that means :-(", e.getMessage());
        }
    }
}
