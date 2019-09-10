package duke;

import duke.command.addDeadlineCommand;
import duke.command.addEventCommand;
import duke.command.AddToDoCommand;
import duke.command.completeCommand;
import duke.command.deleteCommand;
import duke.command.exitCommand;
import duke.command.findCommand;
import duke.command.ListCommand;
import duke.exception.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class chooseCommandTest {
    @Test
    public void testBye() throws DukeException {
        assertEquals(new exitCommand(), chooseCommand.choose("bye"));
    }
    @Test
    public void testList() throws DukeException {
        assertEquals(new ListCommand(), chooseCommand.choose("list"));
    }
    @Test
    public void testDone() throws DukeException {
        assertEquals(new completeCommand(2), chooseCommand.choose("done 2"));
    }
    @Test
    public void testTodo() throws DukeException {
        assertEquals(new AddToDoCommand("borrow book"), chooseCommand.choose("todo borrow book"));
    }
    @Test
    public void testDeadline() throws DukeException {
        assertEquals(new addDeadlineCommand("homework /by 11-11-2019 11:11"),
                chooseCommand.choose("deadline homework /by 11-11-2019 11:11"));
    }
    @Test
    public void testInvalidDeadline() throws DukeException {
        try {
            chooseCommand.choose("deadline testing /by");
            fail();
        }
        catch (DukeException e) {
            assertEquals("☹ OOPS!!! The description of a deadline needs a due date.", e.getMessage());
        }
    }
    @Test
    public void testEvent() throws DukeException {
        assertEquals(new addEventCommand("testing /at 11-11-2019 11:11"), chooseCommand.choose("event testing /at 11-11-2019 11:11"));
    }
    @Test
    public void testInvalid() throws DukeException {
        try {
            chooseCommand.choose("abcd");
            fail();
        }
        catch (DukeException e) {
            assertEquals("☹ OOPS!!! I'm sorry, but I don't know what that means :-(", e.getMessage());
        }
    }
}
