package com.nwjbrandon.duke.services;

import com.nwjbrandon.duke.SystemIO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class TaskManagerTest extends SystemIO {

    @RegisterExtension
    SystemIO io = new SystemIO();

    @Test
    void testTodo() {
        final String testString = "todo borrow book";
        io.provideInput(testString);
        TaskManager taskManager = new TaskManager();
        taskManager.run();
        String output = io.getOutput();
        String expected = "\t____________________________________________________________\n"
                        + "\t Got it. I've added this task:\n"
                        + "\t   [T][✗] borrow book\n"
                        + "\t Now you have 1 tasks in the list.\n"
                        + "\t____________________________________________________________\n";
        assertEquals(expected, output);
    }

    @Test
    void testBye() {
        final String testString = "bye";
        provideInput(testString);
        TaskManager taskManager = new TaskManager();
        boolean status = taskManager.run();
        assertFalse(status);
    }

}
