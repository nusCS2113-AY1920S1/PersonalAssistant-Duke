package duke.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecurringTest {
    @Test
    public void Test() {
        assertEquals("borrow book (frequency: weekly)", new Recurring("borrow book", "T", "weekly").toMessage());
    }
}
