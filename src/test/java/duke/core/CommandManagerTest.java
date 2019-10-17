package duke.core;

import duke.command.*;
import org.junit.jupiter.api.Test;

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
    }
}
