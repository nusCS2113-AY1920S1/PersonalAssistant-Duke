package seedu.duke.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for Event.
 */
public class RangedTaskTest {

    @Test

    /**
     * Dummy test for Event class.
     */
    public void dummyTest() {
        RangedTask task1 = new RangedTask("prepare for midterms", "5/5/2019 1800 and 10/5/2019 1900");

        assertEquals(task1.description, "prepare for midterms");
        assertEquals(task1.dateFrom, "5th of May 2019, 6:00PM");
        assertEquals(task1.dateBy, "10th of May 2019, 7:00PM");
        assertEquals(task1.time, "5/5/2019 1800 and 10/5/2019 1900");
        assertEquals(task1.from, "5/5/2019 1800");
        assertEquals(task1.by, "10/5/2019 1900");

        RangedTask task2 = new RangedTask("meet Kartike.", "next week and two weeks later");
        assertEquals(task2.dateFrom, "next week");
        assertEquals(task2.dateBy, "two weeks later");
        assertEquals(task2.time, "next week and two weeks later");
        assertEquals(task2.from, "next week");
        assertEquals(task2.by, "two weeks later");

    }
}
