package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.TaskList;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author SholihinK
public class CommandTestUtil {
    /**
     * Asserts that the command is unsuccessful and the error message equals to the errorMessage.
     */
    public static void assertCommandFailure(Command command,TaskList taskList, String expectedMessage) {
        try {
            command.commandExecute(taskList);
            throw new AssertionError("CommandException not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }
}
