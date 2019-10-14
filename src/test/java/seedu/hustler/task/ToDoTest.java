package seedu.hustler.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for ToDo.
 */
public class ToDoTest {

    @Test

    /**
     *  Dummy test for ToDo.
     */
    public void dummyTest() {
        ToDo task = new ToDo("Finish work");
        assertEquals(task.description, "Finish work");
    }
}

