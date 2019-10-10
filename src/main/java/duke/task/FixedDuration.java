package duke.task;

import java.time.Duration;


/**
 * Represents a task that has a fixed duration.
 */
public class FixedDuration extends Task {

    protected Duration duration;

    /**
     * Takes in a description of the task and the period of the task.
     * @param description A String representing the task to be completed
     * @param duration A LocalDateTime representing the deadline of the task.
     */
    public FixedDuration(String description, Duration duration) {
        super(description);
        this.duration = duration;
    }

    @Override
    public String toString() {
        long dayCount = duration.toDaysPart();
        String days = dayCount != 1 ? String.format("%d Days ", dayCount) : String.format("%d Day ", dayCount);
        String hms = String.format("%d:%02d:%02d",
                duration.toHoursPart(),
                duration.toMinutesPart(),
                duration.toSecondsPart());
        return "[F]" + super.toString() + " (for: " + days + hms + ")";
    }

}