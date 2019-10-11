package task;

import java.io.Serializable;
import java.time.LocalDateTime;

class Reminder implements Serializable {
    private LocalDateTime reminderDate;

    Reminder(int days, LocalDateTime startDate) {
        reminderDate = startDate.minusDays(days);
    }

    Reminder(int days) {
        reminderDate = LocalDateTime.now().plusDays(days);
    }

    boolean checkReminderTrigger() {
        return LocalDateTime.now().isAfter(reminderDate);
    }
}
