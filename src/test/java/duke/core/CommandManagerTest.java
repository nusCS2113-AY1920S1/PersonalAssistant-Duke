package duke.core;

import duke.command.Command;
import duke.command.AssignTaskToPatientCommand;
import duke.command.DeleteTaskCommand;
import duke.command.AddPatientCommand;
import duke.command.AddStandardTaskCommand;
import duke.command.DeletePatientCommand;
import duke.command.FindPatientCommand;
import duke.command.FindPatientTaskCommand;
import duke.command.HelpCommand;
import duke.command.ListPatientsCommand;
import duke.command.ListTasksCommand;
import duke.command.UpdatePatientCommand;
import duke.command.UpdateTaskCommand;
import duke.command.ExitCommand;
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
