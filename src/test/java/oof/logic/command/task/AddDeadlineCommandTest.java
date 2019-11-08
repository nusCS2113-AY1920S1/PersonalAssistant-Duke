package oof.logic.command.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Test;

import oof.Oof;
import oof.commons.exceptions.ParserException;
import oof.commons.exceptions.command.CommandException;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

//@@author KahLokKee

public class AddDeadlineCommandTest {

    /**
     * Tests the behaviour when description of deadline is not found.
     */
    @Test
    public void execute_DescriptionNotFound_ThrowsException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            new Oof().executeCommand("deadline");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! The deadline needs a description.", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when due date of deadline is not found.
     */
    @Test
    public void execute_DueDateNotFound_ThrowsException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            new Oof().executeCommand("deadline lab");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! The deadline needs a due date.", e.getMessage());
        }
    }


    /**
     * Tests the behaviour when due date of deadline is invalid.
     */
    @Test
    public void execute_DueDateInvalid_ThrowsException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            new Oof().executeCommand("deadline lab /by a");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! The due date is invalid.", e.getMessage());
        }
    }


    /**
     * Tests the behaviour when the description is too long.
     */
    @Test
    public void execute_DescriptionExceedsMaxLength_ThrowsException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            new Oof().executeCommand("deadline abcdefghijklmnopqrstuvwxyz /by 11-11-2020 23:59");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("Task exceeds maximum description length!", e.getMessage());
        }
    }

    /**
     * Tests the behaviour of adding a deadline task.
     *
     * @throws CommandException if command given is invalid.
     * @throws ParserException if command arguments cannot be parsed.
     */
    @Test
    public void execute_CorrectCommandEntered_AddDeadline() throws CommandException, ParserException {
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
        Oof oof = new Oof();
        oof.executeCommand("deadline lab /by 29-10-2019 23:59");
        TaskList taskList = oof.getTaskList();
        Task task = taskList.getTask(taskList.getSize() - 1);
        assertEquals("[D][N] lab (by: 29-10-2019 23:59)", task.toString());
        taskList.deleteTask(taskList.getSize() - 1);
        StorageManager storageManager = oof.getStorageManager();
        storageManager.writeTaskList(taskList);
    }
}
