package seedu.duke.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for deadline.
 */
public class DeadlineTest {

    @Test

    /**
     * Dummy test for deadline.
     */
    public void dummyTest() {
        Deadline task1 = new Deadline("meet Kartike.", "5/5/2019 1800");

        assertEquals(task1.description, "meet Kartike.");
        assertEquals(task1.dateBy, "5th of May 2019, 6:00PM");
        assertEquals(task1.by, "5/5/2019 1800");

        Deadline task2 = new Deadline("meet Kartike.", "next week");
        assertEquals(task2.description, "meet Kartike.");
        assertEquals(task2.dateBy, "next week");
        assertEquals(task2.by, "next week");
    }
}
