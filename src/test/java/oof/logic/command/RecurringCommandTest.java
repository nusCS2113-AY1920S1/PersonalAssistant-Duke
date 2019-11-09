package oof.logic.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import oof.Oof;
import oof.commons.exceptions.command.CommandException;
import oof.commons.exceptions.ParserException;
import oof.model.task.Task;
import oof.model.task.TaskList;

//@@author jasperosy

/**
 * Testing class for RecurringCommand.
 */
class RecurringCommandTest {


    private Oof oof = new Oof();
    private TaskList taskList = oof.getTaskList();

    /**
     * Tests the behaviour when an invalid number of parameters are given.
     */
    @Test
    void parse_RecurringEnteredWithWrongNumberOfParameters_RecursTask() {
        try {
            oof.executeCommand("recurring");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please enter the right number of arguments!", e.getMessage());
        }
        try {
            oof.executeCommand("recurring 1");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please enter the right number of arguments!", e.getMessage());
        }
        try {
            oof.executeCommand("recurring 1 1");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please enter the right number of arguments!", e.getMessage());
        }
        try {
            oof.executeCommand("recurring 1 1 1 1");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please enter the right number of arguments!", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when a non integer index is given.
     */
    @Test
    void parse_RecurringEnteredWithNonIntegerIndex_RecursTask() {
        try {
            oof.executeCommand("recurring a 1 1");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please enter valid numbers!", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when a non integer recurring count is given.
     */
    @Test
    void parse_RecurringEnteredWithNonIntegerCount_RecursTask() {
        try {
            oof.executeCommand("recurring 1 a 1");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please enter valid numbers!", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when a non integer recurring frequency is given.
     */
    @Test
    void parse_RecurringEnteredWithNonIntegerFrequency_RecursTask() {
        try {
            oof.executeCommand("recurring 1 1 a");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please enter valid numbers!", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when an invalid index is given.
     */
    @Test
    void parse_RecurringEnteredWithInvalidIndex_ThrowsException() {
        try {
            oof.executeCommand("recurring -1 1 1");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please select a valid task!", e.getMessage());
        }
        try {
            oof.executeCommand("recurring 0 1 1");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please select a valid task!", e.getMessage());
        }
        try {
            oof.executeCommand("recurring 2147483647 1 1");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please select a valid task!", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when an invalid recurring count is given.
     */
    @Test
    void parse_RecurringEnteredWithInvalidCount_ThrowsException() {
        try {
            oof.executeCommand("recurring 1 -1 1");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please enter a valid number of recurrences!", e.getMessage());
        }
        try {
            oof.executeCommand("recurring 1 11 1");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please enter a valid number of recurrences!", e.getMessage());
        }
        try {
            oof.executeCommand("recurring 1 0 1");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please enter a valid number of recurrences!", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when an invalid frequency is given.
     */
    @Test
    void parse_RecurringEnteredWithInvalidFrequency_ThrowsException() {
        try {
            oof.executeCommand("recurring 1 1 -1");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please enter a valid frequency!", e.getMessage());
        }
        try {
            oof.executeCommand("recurring 1 1 0");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please enter a valid frequency!", e.getMessage());
        }
        try {
            oof.executeCommand("recurring 1 1 5");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please enter a valid frequency!", e.getMessage());
        }
    }

    /**
     * Tests the behaviour for adding Todo Task.
     *
     * @throws CommandException if command is invalid.
     * @throws ParserException  if command cannot be parsed.
     */
    @Test
    void execute_CorrectCommandAndRecurOnce_AddTodo() throws CommandException, ParserException {
        oof.executeCommand("recurring 1 1 1");
        int lastIndex = taskList.getSize() - 1;
        Task task = taskList.getTask(lastIndex);
        assertEquals("[T][N] borrow another book (on: 31-12-2019)", task.toString());
        oof.executeCommand("delete " + (++lastIndex));
        oof.executeCommand("recurring 1 1 2");
        lastIndex = taskList.getSize() - 1;
        task = taskList.getTask(lastIndex);
        assertEquals("[T][N] borrow another book (on: 06-01-2020)", task.toString());
        oof.executeCommand("delete " + (++lastIndex));
        oof.executeCommand("recurring 1 1 3");
        lastIndex = taskList.getSize() - 1;
        task = taskList.getTask(lastIndex);
        assertEquals("[T][N] borrow another book (on: 30-01-2020)", task.toString());
        oof.executeCommand("delete " + (++lastIndex));
        oof.executeCommand("recurring 1 1 4");
        lastIndex = taskList.getSize() - 1;
        task = taskList.getTask(lastIndex);
        assertEquals("[T][N] borrow another book (on: 30-12-2020)", task.toString());
        oof.executeCommand("delete " + (++lastIndex));
    }

    /**
     * Tests the behaviour for adding Deadline.
     *
     * @throws CommandException if command is invalid.
     * @throws ParserException  if command cannot be parsed.
     */
    @Test
    void execute_CorrectCommandAndRecurOnce_AddDeadline() throws CommandException, ParserException {
        oof.executeCommand("recurring 2 1 1");
        int lastIndex = taskList.getSize() - 1;
        Task task = taskList.getTask(lastIndex);
        assertEquals("[D][N] homework (by: 14-10-2019 23:59)", task.toString());
        oof.executeCommand("delete " + (++lastIndex));
        oof.executeCommand("recurring 2 1 2");
        lastIndex = taskList.getSize() - 1;
        task = taskList.getTask(lastIndex);
        assertEquals("[D][N] homework (by: 20-10-2019 23:59)", task.toString());
        oof.executeCommand("delete " + (++lastIndex));
        oof.executeCommand("recurring 2 1 3");
        lastIndex = taskList.getSize() - 1;
        task = taskList.getTask(lastIndex);
        assertEquals("[D][N] homework (by: 13-11-2019 23:59)", task.toString());
        oof.executeCommand("delete " + (++lastIndex));
        oof.executeCommand("recurring 2 1 4");
        lastIndex = taskList.getSize() - 1;
        task = taskList.getTask(lastIndex);
        assertEquals("[D][N] homework (by: 13-10-2020 23:59)", task.toString());
        oof.executeCommand("delete " + (++lastIndex));
    }

    /**
     * Tests the behaviour for adding Deadline.
     *
     * @throws CommandException if command is invalid.
     * @throws ParserException  if command cannot be parsed.
     */
    @Test
    void execute_CorrectCommandAndRecurOnce_AddEvent() throws CommandException, ParserException {
        oof.executeCommand("recurring 3 1 1");
        int lastIndex = taskList.getSize() - 1;
        Task task = taskList.getTask(lastIndex);
        assertEquals("[E][N] lecture (from: 09-10-2019 10:00 to: 09-10-2019 12:00)", task.toString());
        oof.executeCommand("delete " + (++lastIndex));
        oof.executeCommand("recurring 3 1 2");
        lastIndex = taskList.getSize() - 1;
        task = taskList.getTask(lastIndex);
        assertEquals("[E][N] lecture (from: 15-10-2019 10:00 to: 15-10-2019 12:00)", task.toString());
        oof.executeCommand("delete " + (++lastIndex));
        oof.executeCommand("recurring 3 1 3");
        lastIndex = taskList.getSize() - 1;
        task = taskList.getTask(lastIndex);
        assertEquals("[E][N] lecture (from: 08-11-2019 10:00 to: 08-11-2019 12:00)", task.toString());
        oof.executeCommand("delete " + (++lastIndex));
        oof.executeCommand("recurring 3 1 4");
        lastIndex = taskList.getSize() - 1;
        task = taskList.getTask(lastIndex);
        assertEquals("[E][N] lecture (from: 08-10-2020 10:00 to: 08-10-2020 12:00)", task.toString());
        oof.executeCommand("delete " + (++lastIndex));
    }

    /**
     * Tests the behaviour for adding more than one recurring tasks.
     *
     * @throws CommandException if command is invalid.
     * @throws ParserException  if command cannot be parsed.
     */
    @Test
    void execute_CorrectCommandAndRecurMoreThanOnce_AddTasks() throws Exception {
        oof.executeCommand("recurring 1 2 1");
        int lastIndex = taskList.getSize() - 1;
        Task task = taskList.getTask(lastIndex);
        assertEquals("[T][N] borrow another book (on: 01-01-2020)", task.toString());
        oof.executeCommand("delete " + (++lastIndex));
        lastIndex = taskList.getSize() - 1;
        task = taskList.getTask(lastIndex);
        assertEquals("[T][N] borrow another book (on: 31-12-2019)", task.toString());
        oof.executeCommand("delete " + (++lastIndex));
    }
}
