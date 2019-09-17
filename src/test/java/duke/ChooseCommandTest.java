package duke;

import duke.command.CompleteCommand;
import duke.command.ExitCommand;
import duke.command.ListCommand;
import duke.command.AddToDoCommand;
import duke.command.AddDeadlineCommand;
import duke.command.AddEventCommand;
import duke.command.ScheduleCommand;
import duke.exception.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ChooseCommandTest {

    @Test
    public void testBye() throws DukeException {
        assertTrue(ChooseCommand.choose("bye") instanceof ExitCommand);
    }

    @Test
    public void testList() throws DukeException {
        assertTrue(ChooseCommand.choose("list") instanceof ListCommand);
    }

    @Test
    public void testDone() throws DukeException {
        assertTrue(ChooseCommand.choose("done 2") instanceof CompleteCommand);
    }

    @Test
    public void testTodo() throws DukeException {
        assertTrue(ChooseCommand.choose("todo borrow book") instanceof AddToDoCommand);
    }

    @Test
    public void testDeadline() throws DukeException {
        assertTrue(ChooseCommand.choose("deadline homework /by 11-11-2019 11:11") instanceof AddDeadlineCommand);
    }

    @Test
    public void testEvent() throws DukeException {
        assertTrue(ChooseCommand.choose("event testing /at 11-11-2019 11:11") instanceof AddEventCommand);
    }

    @Test
    public void testSchedule() throws DukeException {
        assertTrue(ChooseCommand.choose("schedule 11-11-2019") instanceof ScheduleCommand);
    }

    @Test
    public void testInvalid() throws DukeException {
        try {
            ChooseCommand.choose("abcd");
            fail();
        } catch (DukeException e) {
            assertEquals("OOPS!!! I'm sorry, but I don't know what that means :-(", e.getMessage());
        }
    }
}
