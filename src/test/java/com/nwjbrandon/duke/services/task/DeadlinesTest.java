package com.nwjbrandon.duke.services.task;

import com.nwjbrandon.duke.SystemIO;
import com.nwjbrandon.duke.exceptions.DukeWrongCommandFormatException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class DeadlinesTest {

    @RegisterExtension
    SystemIO io = new SystemIO();

    @Test
    void testTodoIsoDate() throws DukeWrongCommandFormatException {
        new Deadlines("borrow book /by 2/2/2019 1900", 1);
        String output = io.getOutput();
        String expected = "\t____________________________________________________________\n"
                        + "\t Got it. I've added this task:\n"
                        + "\t   [D][✗] borrow book (by: 2nd of February 2019, 7pm)\n"
                        + "\t Now you have 1 tasks in the list.\n"
                        + "\t____________________________________________________________\n";
        assertEquals(expected, output);
    }

    @Test
    void testTodo() throws DukeWrongCommandFormatException {
        new Deadlines("borrow book /by Sunday", 2);
        String output = io.getOutput();
        String expected = "\t____________________________________________________________\n"
                + "\t Got it. I've added this task:\n"
                + "\t   [D][✗] borrow book (by: Sunday)\n"
                + "\t Now you have 2 tasks in the list.\n"
                + "\t____________________________________________________________\n";
        assertEquals(expected, output);
    }

    @Test
    void testGetTaskName() throws DukeWrongCommandFormatException {
        Deadlines task = new Deadlines("borrow book /by Sunday",1);
        String taskName = task.getTaskName();
        assertEquals("borrow book /by Sunday", taskName);
    }

    @Test
    void testGetTaskNameIsoDate() throws DukeWrongCommandFormatException {
        Deadlines task = new Deadlines("borrow book /by 2/2/2019 1900",1);
        String taskName = task.getTaskName();
        assertEquals("borrow book", taskName);
    }

    @Test
    void testGetIsDoneStatus() throws DukeWrongCommandFormatException {
        Deadlines task = new Deadlines("borrow book /by Sunday", 1);
        boolean doneStatus = task.getIsDoneStatus();
        assertFalse(doneStatus);
    }

}
