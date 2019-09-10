package duke.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class TaskTest {

    @Test
    void taskTest() {
        Task task = new Todo("Hey");
        assertEquals("Hey", task.getDescription());
        assertEquals("[✗]", task.getStatusIcon());
        assertEquals("[T][✗] Hey", task.toString());
        task.setStatusIcon(true);
        assertEquals("[T][✓] Hey", task.toString());
    }
}