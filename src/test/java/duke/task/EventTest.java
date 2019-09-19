package duke.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    @Test
    public void testToString() {
        LocalDateTime testTime = LocalDateTime.of(2019, 2, 15, 19, 00);
        Event newEvent = new Event("To Complete Test", testTime);
        assertEquals("[E][NOT DONE] To Complete Test (at: 15/2/2019 1900)", newEvent.toString());

        newEvent.markAsDone();
        assertEquals("[E][DONE] To Complete Test (at: 15/2/2019 1900)", newEvent.toString());
    }
}