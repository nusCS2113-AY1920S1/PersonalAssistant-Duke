package duke.tasks;

import java.time.LocalDateTime;

/**
 * Class for Tasks with a date field.
 */

public class TaskWithDates extends Task {
    protected LocalDateTime startDate;
    Boolean hasDate = false;

    TaskWithDates(String description, LocalDateTime startDate) {
        super(description);
        this.startDate = startDate;
        this.hasDate = true;
    }

    public void updateDate(LocalDateTime newDate) {
        this.startDate = newDate;
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
