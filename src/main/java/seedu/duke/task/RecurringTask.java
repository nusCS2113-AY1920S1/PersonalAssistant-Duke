package seedu.duke.task;

import java.time.LocalDateTime;

import static seedu.duke.parser.DateTimeParser.convertDateTime;
import static seedu.duke.parser.DateTimeParser.toDateTimeString;

/**
 * This task type inherits from Task.
 * It specifies a RecurringTask on a particular time
 * and recurs at a fixed period.
 */
public class RecurringTask extends Task {
    /**
     * LocalDateTime to store the RecurringTask's date and time.
     */
    protected LocalDateTime on;

    /**
     * String description of how frequent the task will recur.
     */
    protected String frequency;

    /**
     * Integer to store the period in minutes of each cycle of the RecurringTask.
     */
    protected int period;

    /**
     * Initializes description and variables.
     */
    public RecurringTask(String description, LocalDateTime at, String frequency, int period) {
        super(description);
        this.on = at;
        this.frequency = frequency;
        this.period = period;
    }

    /**
     * Overrides the toString method in Task to display task type and date time.
     *
     * @return a string with the target info.
     */
    public String toString() {
        return "[RC]" + super.toString() + " (on: " + toDateTimeString(this.getDateTime())
                + ") (Repeats every " + frequency + ")";
    }

    /**
     * Overrides the toSaveFormat function to include task type and date time.
     *
     * @return a string with pipe separated info.
     */
    public String toSaveFormat() {
        return "RC|" + super.toSaveFormat() + "|" + convertDateTime(on)
                + "|" + frequency + "|" + period;
    }

    @Override
    public LocalDateTime getDateTime() {
        if (LocalDateTime.now().isAfter(this.on)) {
            this.on = this.on.plusMinutes(period);
        }
        return this.on;
    }

    @Override
    public void setDateTime(LocalDateTime localDateTime) {
        this.on = localDateTime;
    }
}
