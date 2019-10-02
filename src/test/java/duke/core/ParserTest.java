package duke.core;

import duke.command.AddCommand;
import duke.command.Command;
import duke.command.DeleteCommand;
import duke.command.DoneCommand;
import duke.command.ExitCommand;
import duke.command.FindCommand;
import duke.command.ListCommand;
import duke.command.ViewCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {
    /**
     * Test the return command type of Parser.parse(userInput)
     * @throws DukeException referencing a Duke specified exception with error log
     */
    @Test
    public void commandTypeTest() throws DukeException {
        Command c1 = Parser.parse("bye");
        Command c2 = Parser.parse("done 1");
        Command c3 = Parser.parse("delete 2");
        Command c4 = Parser.parse("list");
        Command c5 = Parser.parse("find MEETING");
        Command c6 = Parser.parse("todo abc");
        Command c7 = Parser.parse("event Meeting /at 27/07/2020 1630");
        Command c8 = Parser.parse("deadline event Homework ABC /by 27/07/2020 1630");
        Command c9 = Parser.parse("view 16/09/2019");

        assertTrue(c1 instanceof ExitCommand, "The command type should be ");
        assertTrue(c2 instanceof DoneCommand, "The command type should be 'DoneCommand'");
        assertTrue(c3 instanceof DeleteCommand, "The command type should be 'DeleteCommand'");
        assertTrue(c4 instanceof ListCommand, "The command type should be 'ListCommand'");
        assertTrue(c5 instanceof FindCommand, "The command type should be 'FindCommand'");
        assertTrue(c6 instanceof AddCommand, "The command type should be 'AddCommand'");
        assertTrue(c7 instanceof AddCommand, "The command type should be 'AddCommand'");
        assertTrue(c8 instanceof AddCommand, "The command type should be 'AddCommand'");
        assertTrue(c9 instanceof ViewCommand, "The command type should be 'ViewCommand'");
    }
}
