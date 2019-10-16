package duke.core;

import duke.command.*;
import org.junit.Test;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandManagerTest {
    /**
     * Test the return command type of Parser.parse(userInput)
     * @throws DukeException referencing a Duke specified exception with error log
     */
    @Test
    public void commandTypeTest() throws DukeException {
        Command c1 = CommandManager.manageCommand("bye");
        Command c2 = CommandManager.manageCommand("done 1");
        Command c3 = CommandManager.manageCommand("delete 2");
        Command c4 = CommandManager.manageCommand("list");
        Command c5 = CommandManager.manageCommand("find MEETING");
        Command c6 = CommandManager.manageCommand("todo abc");
        Command c7 = CommandManager.manageCommand("event Meeting /at 27/07/2020 1630");
        Command c8 = CommandManager.manageCommand("deadline event Homework ABC /by 27/07/2020 1630");
        Command c9 = CommandManager.manageCommand("view 16/09/2019");
        Command c10 = CommandManager.manageCommand("period periodTaskTest /from 27/07/2020 1630 /to 27/08/2020 1630");
        Command c11 = CommandManager.manageCommand("reschedule 1 27/07/2020 1630");

        assertTrue(c1 instanceof ExitCommand, "The command type should be ");
        assertTrue(c2 instanceof DoneCommand, "The command type should be 'DoneCommand'");
        assertTrue(c3 instanceof DeleteCommand, "The command type should be 'DeleteCommand'");
        assertTrue(c4 instanceof ListCommand, "The command type should be 'ListCommand'");
        assertTrue(c5 instanceof FindCommand, "The command type should be 'FindCommand'");
        assertTrue(c6 instanceof AddCommand, "The command type should be 'AddCommand'");
        assertTrue(c7 instanceof AddCommand, "The command type should be 'AddCommand'");
        assertTrue(c8 instanceof AddCommand, "The command type should be 'AddCommand'");
        assertTrue(c9 instanceof ViewCommand, "The command type should be 'ViewCommand'");
        assertTrue(c10 instanceof AddCommand, "The command type should be 'AddCommand'");
        assertTrue(c11 instanceof RescheduleCommand, "The command type should be 'RescheduleCommand'");
    }
}
