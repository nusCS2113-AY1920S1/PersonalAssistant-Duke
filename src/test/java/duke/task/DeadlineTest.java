package duke.task;

import entertainment.pro.model.Deadline;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {
    @Test
    public void testStringConversion() {
        assertEquals("go gym and jog /by 17/09/2019 1800(by: 17th of September 2019, 06:00 PM)",
            (new Deadline("go gym and jog /by 17/09/2019 1800", "D", "17/09/2019 1800")).toMessage());
    }
}