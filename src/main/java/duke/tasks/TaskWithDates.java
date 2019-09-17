package duke.tasks;

import java.time.LocalDateTime;

/**
 * Class for Tasks with a date field.
 */
public class TaskWithDates extends Task {
    TaskWithDates(String description, LocalDateTime startDate) {
        super(description);
        this.startDate = startDate;
        this.hasDate = true;
    }

    /**
     * Constructor to work around Tasks that should contain dates but does not.
     */
    public TaskWithDates(String... description) {
        super(description[0]);
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }
}
