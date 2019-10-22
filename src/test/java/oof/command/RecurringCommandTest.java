package oof.command;

import oof.Oof;
import oof.model.task.TaskList;
import oof.exception.OofException;
import oof.model.task.Task;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class RecurringCommandTest {

    /**
     * Tests the behaviour when an invalid Task index is entered.
     */
    @Test
    public void execute_InvalidIndexEntered_ThrowsOofException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            new Oof().executeCommand("recurring -1 1");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please select a valid task!", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when an invalid number of recurrences is entered.
     */
    @Test
    public void execute_InvalidNumberOfRecurrenceEntered_ThrowsOofException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            new Oof().executeCommand("recurring 1 11");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! The valid number of recurrences is from 1-10!", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when an invalid frequency is entered.
     */
    @Test
    public void execute_InvalidFrequencyEntered_ThrowsOofException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("0".getBytes());
            System.setIn(in);
            new Oof().executeCommand("recurring 1 1");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please enter a valid number!", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when an invalid frequency is entered.
     */
    @Test
    public void execute_NonIntegerFrequencyEntered_ThrowsOofException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("a".getBytes());
            System.setIn(in);
            new Oof().executeCommand("recurring 1 1");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please enter a valid number!", e.getMessage());
        }
    }

    /**
     * Tests the behaviour for adding Todo Task.
     * @throws OofException Execute method throws OofException.
     */
    @Test
    public void execute_CorrectCommandEntered_AddTodo() throws OofException {
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
        new Oof().executeCommand("recurring 1 1");
        TaskList taskList = new Oof().getArr();
        Task task = taskList.getTask(taskList.getSize() - 1);
        assertEquals("[T][N] borrow another book (on: 14-10-2019)", task.toString());
    }

    /**
     * Tests the behaviour for adding Deadline.
     * @throws OofException Execute method throws OofException.
     */
    @Test
    public void execute_CorrectCommandEntered_AddDeadline() throws OofException {
        ByteArrayInputStream in = new ByteArrayInputStream("2".getBytes());
        System.setIn(in);
        new Oof().executeCommand("recurring 2 1");
        TaskList taskList = new Oof().getArr();
        Task task = taskList.getTask(taskList.getSize() - 1);
        assertEquals("[D][N] homework (by: 20-10-2019 23:59)", task.toString());
    }

    /**
     * Tests the behaviour for adding Deadline.
     * @throws OofException Execute method throws OofException.
     */
    @Test
    public void execute_CorrectCommandEntered_AddEvent() throws OofException {
        ByteArrayInputStream in = new ByteArrayInputStream("3".getBytes());
        System.setIn(in);
        new Oof().executeCommand("recurring 3 1");
        TaskList taskList = new Oof().getArr();
        Task task = taskList.getTask(taskList.getSize() - 1);
        assertEquals("[E][N] lecture (from: 08-11-2019 10:00 to: 08-11-2019 12:00)", task.toString());
    }

}
