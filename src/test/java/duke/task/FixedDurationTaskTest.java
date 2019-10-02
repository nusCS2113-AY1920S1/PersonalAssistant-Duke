package duke.task;

import duke.core.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FixedDurationTaskTest {
    /**
     * Test the FixedDurationTask.toString()
     */
    @Test
    public void FixedDurationTaskStringTest() throws DukeException {
        assertEquals("[F][\u2718] FixedDurationTaskTest (duration: 2 hours)", new FixedDurationTask("FixedDurationTaskTest", "2 hours").toString());
    }

    /**
     * Test the fixeddurationtask.writeTxt()
     */
    @Test
    public void writeFormatTest() {
        assertEquals( "F | 0 | FixedDurationTest | 1 hour | false",new FixedDurationTask("FixedDurationTest", "1 hour").writeTxt(), "The writeToFile format is not expected");
    }
}
