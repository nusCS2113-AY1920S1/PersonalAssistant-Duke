package duke.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DetectDuplicateTest {
    @Test
    void detectDuplicateTest() {
        TaskList items = new TaskList();
        DetectDuplicate detectDuplicate = new DetectDuplicate(items);
        detectDuplicate.isDuplicate("todo", "something cool");
        assertEquals("     The same task is already in the list!", detectDuplicate.toString());
    }
}
