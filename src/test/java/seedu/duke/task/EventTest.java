package seedu.duke.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for Event.
 */
public class EventTest {

    @Test

    /**
     * Dummy test for Event class.
     */
    public void dummyTest() {
        Event task1 = new Event("meet Kartike.", "5/5/2019 1800");
        assertEquals(task1.description, "meet Kartike.");
        assertEquals(task1.dateAt, "5th of May 2019, 6:00PM");
        assertEquals(task1.at, "5/5/2019 1800");
        assertEquals(task1.getDateTime(), LocalDateTime.of(2019,5,5,18,0));

        Event task2 = new Event("meet Kartike.", "next week");
        assertEquals(task2.description, "meet Kartike.");
        assertEquals(task2.dateAt, "next week");
        assertEquals(task2.at, "next week");

        Event task3 = new Event("meet Kartike.", "5/5/2019 1200");
        task3.setDateTime(LocalDateTime.of(2019,5,5,18,0));
        assertEquals(task3.getDateTime(), task1.getDateTime());

        Event task4 = new Event("meet Kartike.", "5/5/2019 1200");
        task4.setDateTime("5/5/2019 1800");
        assertEquals(task4.getDateTime(), task1.getDateTime());
    }
}
