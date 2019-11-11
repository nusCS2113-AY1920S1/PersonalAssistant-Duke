package chronologer.task;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Holds the reminder date for the task after processing.
 *
 * @author Fauzan Adipratama
 * @version 1.4
 */
class Reminder implements Serializable {
    LocalDateTime reminderDate;

    Reminder(int days, LocalDateTime startDate) {
        reminderDate = startDate.minusDays(days);
    }

    Reminder(int days) {
        reminderDate = LocalDateTime.now().plusDays(days);
    }

    boolean isReminderTrigger() {
        return (LocalDateTime.now().isAfter(reminderDate) || LocalDateTime.now().isAfter(reminderDate.minusDays(1)));
    }
}
