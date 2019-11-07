package oof.logic.command;


import oof.Oof;
import oof.commons.exceptions.ParserException;
import oof.commons.exceptions.command.CommandException;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.storage.StorageManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

//@@author Kenlhc

public class DoneCommandTest {

    private Oof oof = new Oof();

    /**
     * Tests the behaviour when an invalid index is given.
     */
    @Test
    void execute_DoneEnteredWithInvalidIndex_ThrowsException() {
        try {
            oof.executeCommand("done 100");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! The index is invalid.", e.getMessage());
        }
        try {
            oof.executeCommand("done -1");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! The index is invalid.", e.getMessage());
        }
        try {
            oof.executeCommand("done a");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Please enter a valid number!", e.getMessage());
        }
    }

    /**
     * Tests the behaviour of marking a deadline as done.
     *
     * @throws CommandException if command is invalid.
     * @throws ParserException  if command cannot be parsed.
     */
    @Test
    void execute_CorrectCommandEntered_MarkDeadlineAsDone() throws CommandException, ParserException {
        oof.executeCommand("deadline test /by 30-10-2019 23:59");
        TaskList taskList = oof.getTaskList();
        int lastIndex = taskList.getSize() - 1;
        Task task = taskList.getTask(lastIndex);
        oof.executeCommand("done " + (lastIndex + 1));
        assertTrue(task.getStatus());
        taskList.deleteTask(taskList.getSize() - 1);
        StorageManager storageManager = oof.getStorageManager();
        storageManager.writeTaskList(taskList);
    }

    /**
     * Tests the behaviour of marking a todo as done.
     *
     * @throws CommandException if command is invalid.
     * @throws ParserException  if command cannot be parsed.
     */
    @Test
    void execute_CorrectCommandEntered_MarkTodoAsDone() throws CommandException, ParserException {
        oof.executeCommand("todo buy groceries /on 30-10-2019");
        TaskList taskList = oof.getTaskList();
        int lastIndex = taskList.getSize() - 1;
        Task task = taskList.getTask(lastIndex);
        oof.executeCommand("done " + (lastIndex + 1));
        assertTrue(task.getStatus());
        taskList.deleteTask(taskList.getSize() - 1);
        StorageManager storageManager = oof.getStorageManager();
        storageManager.writeTaskList(taskList);
    }

    /**
     * Tests the behaviour of marking an event as done.
     *
     * @throws CommandException if command is invalid.
     * @throws ParserException  if command cannot be parsed.
     */
    @Test
    void execute_CorrectCommandEntered_MarkEventAsDone() throws CommandException, ParserException {
        oof.executeCommand("event meeting /from 01-11-2019 10:00 /to 01-11-2019 12:00");
        TaskList taskList = oof.getTaskList();
        int lastIndex = taskList.getSize() - 1;
        Task task = taskList.getTask(lastIndex);
        oof.executeCommand("done " + (lastIndex + 1));
        assertTrue(task.getStatus());
        taskList.deleteTask(taskList.getSize() - 1);
        StorageManager storageManager = oof.getStorageManager();
        storageManager.writeTaskList(taskList);
    }
}
