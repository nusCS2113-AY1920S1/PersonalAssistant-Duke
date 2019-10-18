package seedu.hustler.task;

import java.time.LocalDateTime;
import seedu.hustler.Hustler;
import seedu.hustler.task.variables.Difficulty;
import seedu.hustler.task.variables.Tag;

/**
 * A class that inherits from Event. This task type is
 * RecurringEvent which is a recurrent Event.
 */
public class RecurringEvent extends Event {
    /**
     * String description that stats the frequency of recurrence.
     */
    protected String frequency;

    /**
     * Integer to store the period in minutes of each cycle of the RecurringEvent.
     */
    protected int period;

    /**
     * Boolean to keep track of whether next cycle of the RecurringEvent has been added.
     */
    boolean hasRecurred;

    public RecurringEvent(String description, LocalDateTime at, String difficulty, String tag,
                             LocalDateTime now, String frequency, int period, boolean hasRecurred) {
        super(description, at, difficulty, tag, now);
        this.frequency = frequency;
        this.period = period;
        this.hasRecurred = hasRecurred;
    }

    public RecurringEvent(String description, LocalDateTime at, Difficulty difficulty, Tag tag,
                             LocalDateTime now, String frequency, int period) {
        super(description, at, difficulty, tag, now);
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
        if (LocalDateTime.now().isAfter(this.at) && !hasRecurred) {
            addNextRecurrence();
        }
        return this.at;
    }

    @Override
    public void markAsDone() {
        this.isDone = true;
        if (!hasRecurred) {
            addNextRecurrence();
        }
    }

    public void addNextRecurrence() {
        LocalDateTime nextAt = this.at.plusMinutes(period);
        System.out.println("\tNext recurrence of this Event has been added!");
        Hustler.list.add(new RecurringEvent(description, nextAt, difficulty, tag, LocalDateTime.now(), frequency, period));
        this.hasRecurred = true;
    }
}
