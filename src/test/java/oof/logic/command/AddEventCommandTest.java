package oof.logic.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Test;

import oof.Oof;
import oof.commons.exceptions.command.CommandException;
import oof.commons.exceptions.ParserException;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

//@@author KahLokKee

public class AddEventCommandTest {

    /**
     * Tests the behaviour when description of deadline is not found.
     */
    @Test
    public void execute_DescriptionNotFound_ThrowsException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            new Oof().executeCommand("event");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! The event needs a description.", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when start date of an event is not found.
     */
    @Test
    public void execute_StartDateNotFound_ThrowsException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            new Oof().executeCommand("event dinner");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! The event needs a start date.", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when end date of an event is not found.
     */
    @Test
    public void execute_EndDateNotFound_ThrowsException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            new Oof().executeCommand("event dinner /from 29-10-2019 18:00");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! The event needs an end date.", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when start date of an event is invalid.
     */
    @Test
    public void execute_InvalidStartDate_ThrowsException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            new Oof().executeCommand("event dinner /from a /to 02-10-2019 18:00");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! The start date is invalid.", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when end date of an event is invalid.
     */
    @Test
    public void execute_InvalidEndDate_ThrowsException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            new Oof().executeCommand("event dinner /from 02-10-2019 18:00 /to a");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! The end date is invalid.", e.getMessage());
        }
    }

    /**
     * Tests the behaviour of adding an event task.
     *
     * @throws CommandException if command given is invalid.
     * @throws ParserException if command cannot be parsed.
     */
    @Test
    public void execute_CorrectCommandEntered_AddEvent() throws CommandException, ParserException {
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
        Oof oof = new Oof();
        oof.executeCommand("event date /from 29-10-2019 18:00 /to 29-10-2019 23:00");
        TaskList taskList = oof.getTaskList();
        Task task = taskList.getTask(taskList.getSize() - 1);
        assertEquals("[E][N] date (from: 29-10-2019 18:00 to: 29-10-2019 23:00)", task.toString());
        taskList.deleteTask(taskList.getSize() - 1);
        StorageManager storageManager = oof.getStorageManager();
        storageManager.writeTaskList(taskList);
    }
}
