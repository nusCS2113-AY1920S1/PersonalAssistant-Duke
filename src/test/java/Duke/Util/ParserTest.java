package duke.util;

import duke.tasks.*;
import duke.command.*;
import duke.exceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void AddCommandTest() throws DukeException {
        Todo test_todo = new Todo("Do testing now");
        AddCommand add = new AddCommand(test_todo);
        Command hold = Parser.parse("todo Do testing now");
        assertEquals(add, hold);
    }

    @Test
    public void DoneCommandTest() {
        try {
            DoneCommand done = new DoneCommand(3);
            Command hold = Parser.parse("done 3");
            assertEquals(done, hold);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void DeleteCommandTest() {
        try {
            DeleteCommand delete = new DeleteCommand(2);
            Command hold = Parser.parse("delete 2");
            assertEquals(delete, hold);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void FindCommandTest() {
        try {
            FindCommand add = new FindCommand("games test");
            Command hold = Parser.parse("find games test");
            assertEquals(add, hold);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void AllCommandTest() {
        try {
        assertTrue(Parser.parse("bye") instanceof ByeCommand);
        assertTrue(Parser.parse("list") instanceof ListCommand);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testExceptions() {
        DukeException thrown =
                assertThrows(DukeException.class,
                        () -> Parser.parse(""),
                        "Expected to return Command Object but it didn't");
        assertEquals("DukeException: Must be a valid command!", thrown.getMessage());
    }


}
