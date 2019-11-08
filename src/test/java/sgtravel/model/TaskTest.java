package sgtravel.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskTest {
    private Task task = new Task("Go hotel");

    @Test
    void setDescription() {
        task.setDescription("SMU");
        assertEquals(task.getDescription(), "SMU");
        task.setDescription("");
        assertEquals(task.getDescription(), "");
        task.setDescription("\\\\\\\\\\\\");
        assertEquals(task.getDescription(), "\\\\\\\\\\\\");
    }

    @Test
    void getDescription() {
        assertEquals(task.getDescription(), "Go hotel");
        assertNotEquals(task.getDescription(), "go hotel");
        assertNotEquals(task.getDescription(), "Go Hotel");
        assertNotEquals(task.getDescription(), "\\\\\\\\\\\\");
        assertNotEquals(task.getDescription(), "");
    }

    @Test
    void isDone() {
        assertFalse(task.isDone());
        task.setDone(true);
        assertTrue(task.isDone());
    }

    @Test
    void setDone() {
        assertFalse(task.isDone());
        task.setDone(true);
        assertTrue(task.isDone());
        task.setDone(true);
        assertTrue(task.isDone());
        task.setDone(false);
        assertFalse(task.isDone());
        task.setDone(false);
        assertFalse(task.isDone());
    }

    @Test
    void isSameTask() {
        assertTrue(task.isSameTask(task));
        assertTrue(task.isSameTask(new Task("Go hotel")));
        assertFalse(task.isSameTask(new Task("")));
        assertFalse(task.isSameTask(new Task("\\\\\\\\\\\\")));
    }

    @Test
    void testToString() {
        assertEquals(task.toString(), "[✘] Go hotel");
        assertNotEquals(task.toString(), "[✓] Go hotel");
        task.setDone(true);
        assertEquals(task.toString(), "[✓] Go hotel");
        assertNotEquals(task.toString(), "[✘] Go hotel");
        assertNotEquals(task.toString(), "letoh oG ]✘[");
    }
}
