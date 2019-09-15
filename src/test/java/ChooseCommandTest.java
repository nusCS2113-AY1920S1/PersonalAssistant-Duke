package duke;

import duke.command.*;
import duke.command.ListCommand;
import duke.exception.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChooseCommandTest {
    @Test
    public void testBye() throws DukeException {
        assertEquals(new ExitCommand(), ChooseCommand.choose("bye"));
    }
    @Test
    public void testList() throws DukeException {
        assertEquals(new ListCommand(), ChooseCommand.choose("list"));
    }
    @Test
    public void testDone() throws DukeException {
        assertEquals(new CompleteCommand("2"), ChooseCommand.choose("done 2"));
    }
    @Test
    public void testTodo() throws DukeException {
        assertEquals(new AddToDoCommand("borrow book"), ChooseCommand.choose("todo borrow book"));
    }
    @Test
    public void testDeadline() throws DukeException {
        assertEquals(new AddDeadlineCommand("homework /by 11-11-2019 11:11"),
                ChooseCommand.choose("deadline homework /by 11-11-2019 11:11"));
    }
    @Test
    public void testInvalidDeadline() throws DukeException {
        try {
            ChooseCommand.choose("deadline testing /by");
            fail();
        }
        catch (DukeException e) {
            assertEquals("☹ OOPS!!! The description of a deadline needs a due date.", e.getMessage());
        }
    }
    @Test
    public void testEvent() throws DukeException {
        assertEquals(new AddEventCommand("testing /at 11-11-2019 11:11"), ChooseCommand.choose("event testing /at 11-11-2019 11:11"));
    }
    @Test
    public void testInvalid() throws DukeException {
        try {
            ChooseCommand.choose("abcd");
            fail();
        }
        catch (DukeException e) {
            assertEquals("☹ OOPS!!! I'm sorry, but I don't know what that means :-(", e.getMessage());
        }
    }
}
