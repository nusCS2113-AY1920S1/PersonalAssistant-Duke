package duke.task;

import java.text.ParseException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class FixedDurationTest {

    @Test
    void fixeddurationTest() throws ParseException {
        Task task = new FixedDuration("homework", 200, "minutes");
        assertEquals("[F][X] homework (needs 200 minutes)", task.toString());
    }
}