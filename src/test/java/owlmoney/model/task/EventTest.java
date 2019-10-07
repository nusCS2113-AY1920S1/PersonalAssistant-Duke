package owlmoney.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class EventTest {

    @Test
    public void checkEventIsAfterCurrentTest_testDateBeforeCurrent_throwDukeException() throws DukeException {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        LocalDateTime testDate = LocalDateTime.parse("06/06/2019 1200", inputFormatter);
        DukeException err = assertThrows(DukeException.class, () -> Event.checkEventIsAfterCurrent(testDate));
        assertEquals("Time must not be before current time", err.getMessage());
    }

    @Test
    public void checkEventIsAfterCurrentTest_testDateBeforeCurrent_returnTrue() throws DukeException {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        LocalDateTime testDate = LocalDateTime.parse("12/12/2999 1200", inputFormatter);
        assertTrue(Event.checkEventIsAfterCurrent(testDate));
    }
}
