package duke.util;

import duke.command.AddCommand;
import duke.command.ByeCommand;
import duke.command.Command;
import duke.command.DeleteCommand;
import duke.command.DoneCommand;
import duke.command.FindCommand;
import duke.command.ListCommand;
import duke.exceptions.DukeException;
import duke.tasks.Todo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DukeParserTest {

    @Test
    public void addCommandTest() throws DukeException {
        Todo testTempTodo = new Todo("Do testing now");
        AddCommand add = new AddCommand(testTempTodo);
        Command hold = DukeParser.parse("todo Do testing now");
        assertEquals(add, hold);
    }

    @Test
    public void doneCommandTest() {
        try {
            DoneCommand done = new DoneCommand(3);
            Command hold = DukeParser.parse("done 3");
            assertEquals(done, hold);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void deleteCommandTest() {
        try {
            DeleteCommand delete = new DeleteCommand(2);
            Command hold = DukeParser.parse("delete 2");
            assertEquals(delete, hold);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void findCommandTest() {
        try {
            FindCommand add = new FindCommand("games test");
            Command hold = DukeParser.parse("find games test");
            assertEquals(add, hold);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void listCommandTest() {
        try {
            assertTrue(DukeParser.parse("list") instanceof ListCommand);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void byeCommandTest() {
        try {
            assertTrue(DukeParser.parse("bye") instanceof ByeCommand);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testExceptions() {
        DukeException thrown =
            assertThrows(
            DukeException.class,
            () -> DukeParser.parse(""),
            "Expected to return Command Object but it didn't"
            );
        assertEquals("DukeException: Must be a valid command!", thrown.getMessage());
    }


}
