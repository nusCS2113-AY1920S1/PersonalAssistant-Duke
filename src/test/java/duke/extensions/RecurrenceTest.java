package duke.extensions;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import duke.exception.DukeException;
import org.junit.jupiter.api.Test;

class RecurrenceTest {

    @Test
    void testIsTimeToReset_daily() throws DukeException {
        Recurrence r = new Recurrence(Optional.of("daily"));
        assertTrue(!r.isTimeToReset());
    }

    @Test
    void testIsTimeToReset_weekly() throws DukeException {
        Recurrence r = new Recurrence(Optional.of("weekly"));
        assertTrue(!r.isTimeToReset());
    }

}
