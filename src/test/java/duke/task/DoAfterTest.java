package duke.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoAfterTest {
    @Test
    public void testStringConversion() {
        assertEquals("eat later(after: sunday)", (new DoAfter("eat later", "A", "sunday")).toMessage());
        assertEquals("eat later(after: 20/08/2018)", (new DoAfter("eat later", "A", "20/08/2018")).toMessage());
        assertEquals("eat later(after: I do my homework)", (new DoAfter("eat later",
            "A", "I do my homework")).toMessage());


    }
}