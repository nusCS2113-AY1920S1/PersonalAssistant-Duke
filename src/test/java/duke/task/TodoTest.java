package duke.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class TodoTest {

    @Test
    void todoTest() {
        Task task = new Todo("store items");
        assertEquals("[T][X] store items", task.toString());
    }
}