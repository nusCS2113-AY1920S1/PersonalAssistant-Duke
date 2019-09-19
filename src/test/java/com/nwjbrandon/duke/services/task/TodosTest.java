package com.nwjbrandon.duke.services.task;

import com.nwjbrandon.duke.SystemIO;
import com.nwjbrandon.duke.exceptions.DukeWrongCommandFormatException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class TodosTest {

    @RegisterExtension
    SystemIO io = new SystemIO();

    @Test
    void testTodo() throws DukeWrongCommandFormatException {
        new Todos("borrow book", 2);
        String output = io.getOutput();
        String expected = "\t____________________________________________________________\n"
                        + "\t Got it. I've added this task:\n"
                        + "\t   [T][✗] borrow book\n"
                        + "\t Now you have 2 tasks in the list.\n"
                        + "\t____________________________________________________________\n";
        assertEquals(expected, output);
    }

    @Test
    void testGetTaskName() throws DukeWrongCommandFormatException {
        Todos task = new Todos("borrow book",1);
        String taskName = task.getTaskName();
        assertEquals("borrow book", taskName);
    }

    @Test
    void testGetIsDoneStatus() throws DukeWrongCommandFormatException {
        Todos task = new Todos("borrow book", 1);
        boolean doneStatus = task.getIsDoneStatus();
        assertFalse(doneStatus);
    }

}
