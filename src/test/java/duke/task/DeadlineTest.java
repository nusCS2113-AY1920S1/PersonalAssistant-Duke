package duke.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import duke.exception.DukeException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DeadlineTest {

    @Test
    public void checkDeadlineIsAfterCurrentTest_testDateBeforeCurrent_throwDukeException() throws DukeException {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        LocalDateTime testDate = LocalDateTime.parse("06/06/2019 1200", inputFormatter);
        DukeException err = assertThrows(DukeException.class, () -> Deadline.checkDeadlineIsAfterCurrent(testDate));
        assertEquals("Time must not be before current time", err.getMessage());
    }

    @Test
    public void checkDeadlineIsAfterCurrentTest_testDateBeforeCurrent_returnTrue() throws DukeException {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        LocalDateTime testDate = LocalDateTime.parse("12/12/2999 1200", inputFormatter);
        assertTrue(Deadline.checkDeadlineIsAfterCurrent(testDate));
    }
}
