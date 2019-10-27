package duke.commands;

import duke.commands.Command;
import duke.commands.CommandManager;
import duke.commands.functional.ExitCommand;
import duke.exceptions.DukeException;
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

        assertTrue(c1 instanceof ExitCommand, "The command type should be ");
    }
}
