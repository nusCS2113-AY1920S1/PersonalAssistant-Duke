package task;

import exception.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class TodoTest {
    private Todo testTodo = new Todo("Sleep");
    private Todo testTodoOverload = new Todo("0", "Sleep");

    TodoTest() throws DukeException {
    }

    @Test
    void testExceptionThrown() {
    }

    @Test
    void testGetType() {
        assertEquals("T", testTodo.getType());
        assertEquals("T", testTodoOverload.getType());
    }

    @Test
    void testGetStatusIcon() throws DukeException {
        assertEquals("N", testTodo.getStatusIcon());
        assertEquals("N", testTodoOverload.getStatusIcon());
        assertFalse(testTodo.checkCompletion());
        assertFalse(testTodoOverload.checkCompletion());
        testTodo.markDone();
        testTodoOverload.markDone();
        assertTrue(testTodo.checkCompletion());
        assertTrue(testTodoOverload.checkCompletion());
        assertEquals("Y", testTodo.getStatusIcon());
        assertEquals("Y", testTodoOverload.getStatusIcon());

        try {
            testTodo.markDone();
            fail();
        } catch (Exception e) {
            assertEquals("But good sir, this task is already done!", e.getMessage());
        }

        try {
            testTodoOverload.markDone();
            fail();
        } catch (Exception e) {
            assertEquals("But good sir, this task is already done!", e.getMessage());
        }
    }

    @Test
    void testToList() {
        assertEquals("[T][N] Sleep", testTodo.toList());
        assertEquals("[T][N] Sleep", testTodoOverload.toList());
    }

    @Test
    void testGetDescription() {
        assertEquals("Sleep", testTodo.getDescription());
        assertEquals("Sleep", testTodoOverload.getDescription());
    }
}