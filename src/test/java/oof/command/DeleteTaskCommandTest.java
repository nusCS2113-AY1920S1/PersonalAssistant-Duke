package oof.command;

import oof.Oof;
import oof.exception.ParserException;
import oof.exception.command.CommandException;
import oof.model.task.Task;
import oof.model.task.TaskList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class DeleteTaskCommandTest {

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
     * Test behavior when index is of negative value.
     */
    @Test
    public void execute_negativeIndex_exceptionThrown() {
        try {
            oof.executeCommand("delete -1");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Invalid number!", e.getMessage());
        }
    }

    /**
     * Test behavior when index is a String.
     */
    @Test
    public void execute_stringIndex_exceptionThrown() {
        try {
            oof.executeCommand("delete abc");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please enter a valid number!", e.getMessage());
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
        int size = taskList.getSize() - 1;
        final Task before = taskList.getTask(size);
        oof.executeCommand("todo test /on 06-11-2019");
        size = taskList.getSize();
        oof.executeCommand("delete " + size);
        size = taskList.getSize() - 1;
        Task after = taskList.getTask(size);
        assertEquals(before, after);
    }
}
