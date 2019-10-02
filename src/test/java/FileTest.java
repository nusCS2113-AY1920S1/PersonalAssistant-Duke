import duke.tasks.FileTask;
import duke.tasks.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileTest {

    @org.junit.jupiter.api.Test
    void testToString_createAndMarkDoneNewTask() {
        Task task = new FileTask("file1");
        assertEquals("[✗] file1", task.toString());
        task.markDone();
        assertEquals("[✓] file1", task.toString());
    }

    @org.junit.jupiter.api.Test
    void testStoreString_createAndMarkDoneNewTask() {
        Task task = new FileTask("file1");
        assertEquals("FILE | 0 | file1", task.storeString());
        task.markDone();
        assertEquals("FILE | 1 | file1", task.storeString());
    }
}
