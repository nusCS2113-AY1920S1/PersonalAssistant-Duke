import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    @Test
    void testEvent() {
        Event event = new Event("dinner", "28/9/2019 1800");
        assertEquals(event.toString(), "[E][✘] dinner (at: Sat Sep 28 18:00:00 SGT 2019)");
    }
}
