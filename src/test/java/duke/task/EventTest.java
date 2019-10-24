package duke.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    @Test
    public void testStringConversion() {
        assertEquals("eat later /at 20/09/2019 1800(at: 20th of September 2019, "
                + "06:00 PM to 21st of September 2019, 06:00 PM)",
            (new Event("eat later /at 20/09/2019 1800", "T", "20/09/2019 1800", "21/09/2019 1800")).toMessage());

    }
}
