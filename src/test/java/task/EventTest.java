package task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    @Test
    public void testStringConversion() {
        assertEquals("eat later /at 20/09/2019 1800(at: 20/09/2019 1800)", (new Event("eat later /at 20/09/2019 1800", "T", "20/09/2019 1800")).toMessage());
    }
}
