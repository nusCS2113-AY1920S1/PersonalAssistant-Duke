package oof.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Test;

import oof.Oof;
import oof.exception.CommandException.CommandException;
import oof.exception.ParserException;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

//@@author KahLokKee

public class AddToDoCommandTest {

    /**
     * Tests the behaviour when description of todo is not found.
     */
    @Test
    public void execute_DescriptionNotFound_ThrowsException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            new Oof().executeCommand("todo");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! The todo needs a description.", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when date of todo is not found.
     */
    @Test
    public void execute_DateNotFound_ThrowsException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            new Oof().executeCommand("todo buy groceries");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! The todo needs a date.", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when date of a todo is invalid.
     */
    @Test
    public void execute_InvalidDate_ThrowsException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            new Oof().executeCommand("todo buy groceries /on a");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! The date is invalid.", e.getMessage());
        }
    }

    /**
     * Tests the behaviour of adding a todo task.
     *
     * @throws CommandException if command given is invalid.
     * @throws ParserException if command cannot be parsed.
     */
    @Test
    public void execute_CorrectCommandEntered_AddToDo() throws CommandException, ParserException {
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
        Oof oof = new Oof();
        oof.executeCommand("todo buy groceries /on 29-10-2019");
        TaskList taskList = oof.getTaskList();
        Task task = taskList.getTask(taskList.getSize() - 1);
        assertEquals("[T][N] buy groceries (on: 29-10-2019)", task.toString());
        taskList.deleteTask(taskList.getSize() - 1);
        StorageManager storageManager = oof.getStorageManager();
        storageManager.writeTaskList(taskList);
    }
}
