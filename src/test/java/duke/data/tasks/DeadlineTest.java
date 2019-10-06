package duke.data.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeadlineTest {

    @Test
    void testToString() {
        Deadline deadline = new Deadline("Homework", "tomorrow");
        assertEquals(deadline.toString(), "[D][✘] Homework (by: tomorrow)");
    }
}
