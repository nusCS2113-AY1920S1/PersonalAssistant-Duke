package seedu.duke.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.duke.parser.DateTimeParser.getDateTime;

/**
 * Test class for Event.
 */
public class RangedTaskTest {

    @Test

    /**
     * Dummy test for Event class.
     */
    public void dummyTest() {

        LocalDateTime from = getDateTime("5/5/2019 1800");
        LocalDateTime by = getDateTime("10/5/2019 1900");

        RangedTask task1 = new RangedTask("prepare for midterms", from,by);

        assertEquals(task1.description, "prepare for midterms");
//        assertEquals(task1.from, "5th of May 2019, 6:00PM");
//        assertEquals(task1.by, "10th of May 2019, 7:00PM");
        assertEquals(task1.from, from);
        assertEquals(task1.by, by);

//        RangedTask task2 = new RangedTask("meet Kartike.", "next week and two weeks later");
//        assertEquals(task2.dateFrom, "next week");
//        assertEquals(task2.dateBy, "two weeks later");
//        assertEquals(task2.time, "next week and two weeks later");
//        assertEquals(task2.from, "next week");
//        assertEquals(task2.by, "two weeks later");

    }
}
