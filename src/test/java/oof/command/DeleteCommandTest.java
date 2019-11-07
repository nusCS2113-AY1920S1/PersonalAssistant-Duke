package oof.command;

import oof.Oof;
import oof.exception.ParserException;
import oof.exception.command.CommandException;
import oof.model.task.TaskList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


public class DeleteCommandTest {

    private Oof oof = new Oof();
    private TaskList taskList = oof.getTaskList();

    /**
     * Test behavior when index is invalid.
     */
    @Test
    public void execute_invalidIndex_exceptionThrown() {
        try {
            oof.executeCommand("delete 12345");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Invalid number!", e.getMessage());
        }
    }

    /**
     * Test behavior for deleting Task.
     *
     * @throws CommandException if command in invalid.
     * @throws ParserException if command cannot be parsed.
     */
    @Test
    public void execute_correctIndex_deleteTask() throws CommandException, ParserException {
        oof.executeCommand("todo test /on 06-11-2019");
        int index = taskList.getSize() - 1;
        String before = taskList.getTask(index).toString();

        int recent = index + 1;
        oof.executeCommand("delete " + recent);
        index = taskList.getSize() - 1;
        String after = taskList.getTask(index).toString();

        boolean isDeleted = false;
        if (!before.equals(after)) {
            isDeleted = true;
        }
        assertTrue(isDeleted);
    }
}
