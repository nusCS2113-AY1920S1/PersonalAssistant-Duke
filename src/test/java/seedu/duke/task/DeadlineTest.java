package seedu.duke.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

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
        assertEquals(task1.getDateTime(), LocalDateTime.of(2019,5,5,18,0));

        Deadline task2 = new Deadline("meet Kartike.", "next week");
        assertEquals(task2.description, "meet Kartike.");
        assertEquals(task2.dateBy, "next week");
        assertEquals(task2.by, "next week");

        Deadline task3 = new Deadline("meet Kartike.", "5/5/2019 1200");
        task3.setDateTime(LocalDateTime.of(2019,5,5,18,0));
        assertEquals(task3.getDateTime(), task1.getDateTime());

        Deadline task4 = new Deadline("meet Kartike.", "5/5/2019 1200");
        task4.setDateTime("5/5/2019 1800");
        assertEquals(task4.getDateTime(), task1.getDateTime());
    }
}
