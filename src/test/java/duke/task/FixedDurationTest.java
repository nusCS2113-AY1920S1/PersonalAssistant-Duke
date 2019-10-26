package duke.task;

import java.text.ParseException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


//@@author Dou-Maokang
/**
 * A test class to test the correctness of FixedDuration class.
 */
class FixedDurationTest {

    @Test
    void fixeddurationTest() throws ParseException {
        Task task1 = new FixedDuration("homework", 200, "minutes");
        assertEquals("[F][X] homework (needs 200 minutes)", task1.toString());

        Task task2 = new FixedDuration("workout", 20, "min");
        assertEquals("[F][X] workout (needs 20 minutes)", task2.toString());

        Task task3 = new FixedDuration("read", 2, "hours");
        assertEquals("[F][X] read (needs 2 hours)", task3.toString());

        Task task4 = new FixedDuration("sing", 1, "hr");
        assertEquals("[F][X] sing (needs 1 hour)", task4.toString());
    }

}
//@@author