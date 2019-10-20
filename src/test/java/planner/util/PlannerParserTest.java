package planner.util;

import planner.command.AddCommand;
import planner.command.ByeCommand;
import planner.command.Command;
import planner.command.DeleteCommand;
import planner.command.DoneCommand;
import planner.command.FindCommand;
import planner.command.ListCommand;
import planner.exceptions.ModException;
import planner.modules.Todo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlannerParserTest {

    @Test
    public void addCommandTest() throws ModException {
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
        } catch (ModException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void deleteCommandTest() {
        try {
            DeleteCommand delete = new DeleteCommand(2);
            Command hold = DukeParser.parse("delete 2");
            assertEquals(delete, hold);
        } catch (ModException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void findCommandTest() {
        try {
            FindCommand add = new FindCommand("games test");
            Command hold = DukeParser.parse("find games test");
            assertEquals(add, hold);
        } catch (ModException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void listCommandTest() {
        try {
            assertTrue(DukeParser.parse("list") instanceof ListCommand);
        } catch (ModException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void byeCommandTest() {
        try {
            assertTrue(DukeParser.parse("bye") instanceof ByeCommand);
        } catch (ModException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testExceptions() {
        ModException thrown =
            assertThrows(
            ModException.class,
            () -> DukeParser.parse(""),
            "Expected to return Command Object but it didn't"
            );
        assertEquals("Error: Must be a valid command!", thrown.getMessage());
    }

}
