package duke.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Reminder to a Task.
 *
 * @author Pang Jia Jun Vernon
 */
public class Reminder {
    // TODO: Universal LocalDateTime parser
    private static final DateTimeFormatter PAT_DATETIME = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter PAT_DATETIME_DISPLAY = DateTimeFormatter.ofPattern("eee, d MMM yyyy h:mm a");

    private LocalDateTime datetime;

    /**
     * Creates a reminder with the specified date and time.
     *
     * @param datetime The reminder's date and time.
     */
    public Reminder(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    /**
     * Formats the reminder for display to the user.
     *
     * @return Display-formatted description of the reminder.
     */
    @Override
    public String toString() {
        return "[R: " + datetime.format(PAT_DATETIME_DISPLAY) + "]";
    }

    /**
     * Formats the reminder to write to the data file.
     *
     * @return Data-formatted (tab-separated) description of the reminder.
     */
    public String toData() {
        return datetime.format(PAT_DATETIME);
    }

    /**
     * Gets the reminder's date and time.
     *
     * @return A LocalDateTime object representing the reminder's date and time.
     */
    public LocalDateTime getDateTime() {
        return datetime;
    }
}
