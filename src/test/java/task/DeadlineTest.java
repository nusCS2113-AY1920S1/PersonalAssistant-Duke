package task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {
    @Test
    public void testStringConversion() {
        assertEquals("go gym and jog /by 17/09/2019 1800(by: 17/09/2019 1800)", (new Deadline("go gym and jog /by 17/09/2019 1800", "D", "17/09/2019 1800")).toMessage());
    }
}