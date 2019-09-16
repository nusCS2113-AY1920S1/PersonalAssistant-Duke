package duke.task;

import duke.core.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PeriodTaskTest {
    /**
     * Test the PeriodTask.toString()
     */
    @Test
    public void PeriodTaskStringTest() throws DukeException {
        assertEquals("[P][\u2718] PeriodTaskTest (start: 27th of July 1996, 3PM end: 27th of July 2020, 3PM)", new PeriodTask("PeriodTaskTest", "27/07/1996 1530", "27/07/2020 1530").toString(), "toString result is not expected");
    }

    /**
     * Test the PeriodTask.writeTxt()
     */
    @Test
    public void writeFormatTest() throws DukeException {
        assertEquals( "P | 0 | PeriodTaskTest | 27/07/1996 1530 | 27/07/2020 1530",new PeriodTask("PeriodTaskTest", "27/07/1996 1530", "27/07/2020 1530").writeTxt(), "The writeToFile format is not expected");
    }
}