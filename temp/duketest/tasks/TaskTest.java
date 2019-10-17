package duketest.tasks;

import duke.tasks.Task;
import duke.tasks.Todo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TaskTest {

    @Test
    public void checkGetStatusIcon() {
        Task obj = new Todo("borrow book");
        assertEquals("N",obj.getStatusIcon());
    }

    @Test
    public void checkMarkAsDone() {
        Task obj = new Todo("join cca");
        obj.markAsDone();
        assertTrue(obj.isDone);
    }

    @Test
    public void testCheckKeyword() {
        Task obj = new Todo("leave cca");
        assertFalse(obj.checkKeyword("book"));
    }
}
