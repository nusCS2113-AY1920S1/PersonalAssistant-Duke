package duke.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeadlineTest {
    Deadline deadline = new Deadline("Test String", "Test date");

    @Test
    void testToString1() {
        assertEquals(deadline.toString(), "[D][\u2718] Test String (by: Test date)\n");
    }

    @Test
    void testGetSymbol() {
        assertEquals(deadline.getSymbol(), "[D]");
    }

    @Test
    void testWriteToFile() {
        deadline.markAsDone();
        assertEquals(deadline.writeToFile(), "D | 1 | Test String | Test date");
    }
}