import duke.data.Reminder;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JUnit class testing the class Reminder.
 *
 * @author Pang Jia Jun Vernon
 */
class ReminderTest {
    private static final DateTimeFormatter PAT_DATETIME = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter PAT_DATETIME_DISPLAY = DateTimeFormatter.ofPattern("eee, d MMM yyyy h:mm a");

    private LocalDateTime reminderDateTime = LocalDateTime.now();
    private Reminder reminder = new Reminder(reminderDateTime);

    /**
     * Compares the LocalDateTime object returned by getDatetime() called by the Reminder object with the LocalDateTime
     * object created in this JUnit class. Expect both objects to be equal.
     */
    @Test
    public void testGetDatetime_datetimeNow_correctDatetime() {
        assertEquals(reminder.getDateTime(), reminderDateTime);
    }

    /**
     * Compares the output returned by toString() called by the Reminder object with the correct output.
     * Expect them to be equal.
     */
    @Test
    public void testToString_datetimeNow_correctOutput() {
        assertEquals(reminder.toString(), "[R: " + reminderDateTime.format(PAT_DATETIME_DISPLAY) + "]");
    }

    /**
     * Compares the output returned by toData() called by the Reminder object with the correct output.
     * Expect them to be equal.
     */
    @Test
    public void testToData_datetimeNow_correctOutput() {
        assertEquals(reminder.toData(), reminderDateTime.format(PAT_DATETIME));
    }
}