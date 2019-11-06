package duke.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

//@@author talesrune
class TaskTest {

    @Test
    void taskTest() {
        Task task = new Todo("Hey");
        assertEquals("Hey", task.getDescription());
        assertEquals("[X]", task.getStatusIcon());
        assertEquals("[T][X] Hey", task.toString());
        task.setStatusIcon(true);
        assertEquals("[T][/] Hey", task.toString());
    }
}