package duke.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FixedTest {
    @Test
    public void test() {
        assertEquals("borrow book (duration: 2 hours)", new FixedDuration("borrow book", "F", "2 hours").toMessage());
    }
}
