package seedu.hustler.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.hustler.parser.DateTimeParser.getDateTime;

/**
 * Test class for Event.
 */
public class EventTest {

    @Test

    /**
     * Dummy test for Event class.
     */
    public void dummyTest() {

        LocalDateTime localDateTimeOne = getDateTime("5/5/2019 1800");
        LocalDateTime localDateTimeTwo = getDateTime("5/5/2019 1200");

        Event task1 = new Event("meet Kartike.", localDateTimeOne);
        assertEquals(task1.description, "meet Kartike.");
//        assertEquals(task1.dateAt, "5th of May 2019, 6:00PM");
        assertEquals(task1.at, localDateTimeOne);
        assertEquals(task1.getDateTime(), LocalDateTime.of(2019,5,5,18,0));

//        Event task2 = new Event("meet Kartike.", "next week");
//        assertEquals(task2.description, "meet Kartike.");
//        assertEquals(task2.dateAt, "next week");
//        assertEquals(task2.at, "next week");

        Event task3 = new Event("meet Kartike.", localDateTimeTwo);
        task3.setDateTime(LocalDateTime.of(2019,5,5,18,0));
        assertEquals(task3.getDateTime(), task1.getDateTime());

        Event task4 = new Event("meet Kartike.", localDateTimeTwo);
        task4.setDateTime(localDateTimeOne);
        assertEquals(task4.getDateTime(), task1.getDateTime());
    }
}
