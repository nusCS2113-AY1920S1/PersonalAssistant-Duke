import exception.DukeException;
import org.junit.jupiter.api.Test;
import task.Event;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    @Test
    void testEvent() throws DukeException {
        Event event = new Event("dinner", "28/9/2019 1800", "28/9/2019 2000");
        assertEquals(event.toString(),
                "[E][✘] dinner (at: Sat Sep 28 18:00:00 SGT 2019 to Sat Sep 28 20:00:00 SGT 2019)");
    }
}
