package seedu.hustler.task;

import java.time.LocalDateTime;
import seedu.hustler.Hustler;
import seedu.hustler.task.variables.Difficulty;
import seedu.hustler.task.variables.Tag;

/**
 * A class that inherits from Deadline. This task type is
 * RecurringDeadline which is a recurrent Deadline.
 */
public class RecurringDeadline extends Deadline {
    /**
     * String description that stats the frequency of recurrence.
     */
    protected String frequency;

    /**
     * Integer to store the period in minutes of each cycle of the RecurringDeadline.
     */
    protected int period;

    /**
     * Boolean to keep track of whether next cycle of the RecurringDeadline has been added.
     */
    boolean hasRecurred;

    /**
     * Initializes RecurringDeadline's attributes.
     */
    public RecurringDeadline(String description, LocalDateTime by, String difficulty, String tag,
                             LocalDateTime now, String frequency, int period, boolean hasRecurred) {
        super(description, by, difficulty, tag, now);
        this.frequency = frequency;
        this.period = period;
        this.hasRecurred = hasRecurred;
    }

    /**
     * Initializes RecurringDeadline's attributes.
     */
    public RecurringDeadline(String description, LocalDateTime by, Difficulty difficulty, Tag tag,
                             LocalDateTime now, String frequency, int period) {
        super(description, by, difficulty, tag, now);
        this.frequency = frequency;
        this.period = period;
        this.hasRecurred = false;
    }

    @Override
    public String toString() {
        return super.toString() + " (Repeats every " + frequency + ")";
    }

    @Override
    public String toSaveFormat() {
        return super.toSaveFormat() + "|" + frequency + "|" + period + "|" + hasRecurred;
    }

    @Override
    public LocalDateTime getDateTime() {
        if (LocalDateTime.now().isAfter(this.by) && !hasRecurred) {
            addNextRecurrence();
        }
        return this.by;
    }

    @Override
    public void markAsDone() {
        this.isDone = true;
        if (!hasRecurred) {
            addNextRecurrence();
        }
    }

    /**
     * Adds the next recurrence of the RecurringDeadline
     * to the task list based on its period.
     */
    public void addNextRecurrence() {
        LocalDateTime nextBy = this.by.plusMinutes(period);
        System.out.println("\tNext recurrence of this Deadline has been added!");
        Hustler.list.add(new RecurringDeadline(description, nextBy, difficulty, tag,
                LocalDateTime.now(), frequency, period));
        this.hasRecurred = true;
    }
}
