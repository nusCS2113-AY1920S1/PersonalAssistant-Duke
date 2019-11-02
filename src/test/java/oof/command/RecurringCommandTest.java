package oof.command;

import oof.Oof;
import oof.model.task.TaskList;
import oof.exception.OofException;
import oof.model.task.Task;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class RecurringCommandTest {


    /**
     * Tests the behaviour when an invalid number of parameters are given.
     */
    @Test
    void parse_RecurringEnteredWithWrongNumberOfParameters_RecursTask() {
        try {
            new Oof().executeCommand("recurring");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please enter the right number of arguments!", e.getMessage());
        }
        try {
            new Oof().executeCommand("recurring 1");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please enter the right number of arguments!", e.getMessage());
        }
        try {
            new Oof().executeCommand("recurring 1 1");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please enter the right number of arguments!", e.getMessage());
        }
        try {
            new Oof().executeCommand("recurring 1 1 1 1");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please enter the right number of arguments!", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when a non integer index is given.
     */
    @Test
    void parse_RecurringEnteredWithNonIntegerIndex_RecursTask() {
        try {
            new Oof().executeCommand("recurring a 1 1");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please enter valid numbers!", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when a non integer recurring count is given.
     */
    @Test
    void parse_RecurringEnteredWithNonIntegerCount_RecursTask() {
        try {
            new Oof().executeCommand("recurring 1 a 1");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please enter valid numbers!", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when a non integer recurring frequency is given.
     */
    @Test
    void parse_RecurringEnteredWithNonIntegerFrequency_RecursTask() {
        try {
            new Oof().executeCommand("recurring 1 1 a");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please enter valid numbers!", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when an invalid index is given.
     */
    @Test
    void parse_RecurringEnteredWithInvalidIndex_ThrowsOofException() {
        try {
            new Oof().executeCommand("recurring -1 1 1");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please select a valid task!", e.getMessage());
        }
        try {
            new Oof().executeCommand("recurring 0 1 1");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please select a valid task!", e.getMessage());
        }
        try {
            new Oof().executeCommand("recurring 2147483647 1 1");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please select a valid task!", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when an invalid recurring count is given.
     */
    @Test
    void parse_RecurringEnteredWithInvalidCount_ThrowsOofException() {
        try {
            new Oof().executeCommand("recurring 1 -1 1");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please enter a valid number of recurrences!", e.getMessage());
        }
        try {
            new Oof().executeCommand("recurring 1 11 1");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please enter a valid number of recurrences!", e.getMessage());
        }
        try {
            new Oof().executeCommand("recurring 1 0 1");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please enter a valid number of recurrences!", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when an invalid frequency is given.
     */
    @Test
    void parse_RecurringEnteredWithInvalidFrequency_ThrowsOofException() {
        try {
            new Oof().executeCommand("recurring 1 1 -1");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please enter a valid frequency!", e.getMessage());
        }
        try {
            new Oof().executeCommand("recurring 1 1 0");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please enter a valid frequency!", e.getMessage());
        }
        try {
            new Oof().executeCommand("recurring 1 1 5");
            fail();
        } catch (OofException e) {
            assertEquals("OOPS!!! Please enter a valid frequency!", e.getMessage());
        }
    }

    /**
     * Tests the behaviour for adding Todo Task.
     *
     * @throws OofException Execute method throws OofException.
     */
    @Test
    void execute_CorrectCommandAndRecurOnce_AddTodo() throws OofException {
        new Oof().executeCommand("recurring 1 1 1");
        TaskList taskList = new Oof().getTaskList();
        int lastIndex = taskList.getSize() - 1;
        Task task = taskList.getTask(lastIndex);
        new Oof().executeCommand("delete " + (++lastIndex));
        assertEquals("[T][N] borrow another book (on: 31-12-2019)", task.toString());
        new Oof().executeCommand("recurring 1 1 2");
        taskList = new Oof().getTaskList();
        lastIndex = taskList.getSize() - 1;
        task = taskList.getTask(lastIndex);
        new Oof().executeCommand("delete " + (++lastIndex));
        assertEquals("[T][N] borrow another book (on: 06-01-2020)", task.toString());
        new Oof().executeCommand("recurring 1 1 3");
        taskList = new Oof().getTaskList();
        lastIndex = taskList.getSize() - 1;
        task = taskList.getTask(lastIndex);
        new Oof().executeCommand("delete " + (++lastIndex));
        assertEquals("[T][N] borrow another book (on: 30-01-2020)", task.toString());
        new Oof().executeCommand("recurring 1 1 4");
        taskList = new Oof().getTaskList();
        lastIndex = taskList.getSize() - 1;
        task = taskList.getTask(lastIndex);
        new Oof().executeCommand("delete " + (++lastIndex));
        assertEquals("[T][N] borrow another book (on: 30-12-2020)", task.toString());
    }

    /**
     * Tests the behaviour for adding Deadline.
     *
     * @throws OofException Execute method throws OofException.
     */
    @Test
    void execute_CorrectCommandAndRecurOnce_AddDeadline() throws OofException {
        new Oof().executeCommand("recurring 2 1 1");
        TaskList taskList = new Oof().getTaskList();
        int lastIndex = taskList.getSize() - 1;
        Task task = taskList.getTask(lastIndex);
        new Oof().executeCommand("delete " + (++lastIndex));
        assertEquals("[D][N] homework (by: 14-10-2019 23:59)", task.toString());
        new Oof().executeCommand("recurring 2 1 2");
        taskList = new Oof().getTaskList();
        lastIndex = taskList.getSize() - 1;
        task = taskList.getTask(lastIndex);
        new Oof().executeCommand("delete " + (++lastIndex));
        assertEquals("[D][N] homework (by: 20-10-2019 23:59)", task.toString());
        new Oof().executeCommand("recurring 2 1 3");
        taskList = new Oof().getTaskList();
        lastIndex = taskList.getSize() - 1;
        task = taskList.getTask(lastIndex);
        new Oof().executeCommand("delete " + (++lastIndex));
        assertEquals("[D][N] homework (by: 13-11-2019 23:59)", task.toString());
        new Oof().executeCommand("recurring 2 1 4");
        taskList = new Oof().getTaskList();
        lastIndex = taskList.getSize() - 1;
        task = taskList.getTask(lastIndex);
        new Oof().executeCommand("delete " + (++lastIndex));
        assertEquals("[D][N] homework (by: 13-10-2020 23:59)", task.toString());
    }

    /**
     * Tests the behaviour for adding Deadline.
     *
     * @throws OofException Execute method throws OofException.
     */
    @Test
    void execute_CorrectCommandAndRecurOnce_AddEvent() throws OofException {
        new Oof().executeCommand("recurring 3 1 1");
        TaskList taskList = new Oof().getTaskList();
        int lastIndex = taskList.getSize() - 1;
        Task task = taskList.getTask(lastIndex);
        new Oof().executeCommand("delete " + (++lastIndex));
        assertEquals("[E][N] lecture (from: 09-10-2019 10:00 to: 09-10-2019 12:00)", task.toString());
        new Oof().executeCommand("recurring 3 1 2");
        taskList = new Oof().getTaskList();
        lastIndex = taskList.getSize() - 1;
        task = taskList.getTask(lastIndex);
        new Oof().executeCommand("delete " + (++lastIndex));
        assertEquals("[E][N] lecture (from: 15-10-2019 10:00 to: 15-10-2019 12:00)", task.toString());
        new Oof().executeCommand("recurring 3 1 3");
        taskList = new Oof().getTaskList();
        lastIndex = taskList.getSize() - 1;
        task = taskList.getTask(lastIndex);
        new Oof().executeCommand("delete " + (++lastIndex));
        assertEquals("[E][N] lecture (from: 08-11-2019 10:00 to: 08-11-2019 12:00)", task.toString());
        new Oof().executeCommand("recurring 3 1 4");
        taskList = new Oof().getTaskList();
        lastIndex = taskList.getSize() - 1;
        task = taskList.getTask(lastIndex);
        new Oof().executeCommand("delete " + (++lastIndex));
        assertEquals("[E][N] lecture (from: 08-10-2020 10:00 to: 08-10-2020 12:00)", task.toString());
    }

    /**
     * Tests the behaviour for adding more than one recurring tasks.
     *
     * @throws OofException Execute method throws OofException.
     */
    @Test
    void execute_CorrectCommandAndRecurMoreThanOnce_AddTasks() throws OofException {
        new Oof().executeCommand("recurring 1 2 1");
        TaskList taskList = new Oof().getTaskList();
        int lastIndex = taskList.getSize() - 1;
        Task task = taskList.getTask(lastIndex);
        new Oof().executeCommand("delete " + (++lastIndex));
        assertEquals("[T][N] borrow another book (on: 01-01-2020)", task.toString());
        lastIndex = taskList.getSize() - 1;
        task = taskList.getTask(--lastIndex);
        new Oof().executeCommand("delete " + (++lastIndex));
        assertEquals("[T][N] borrow another book (on: 31-12-2019)", task.toString());
    }
}
