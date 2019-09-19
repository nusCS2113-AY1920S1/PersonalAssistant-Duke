package duke.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class that represents a deadline task that allows for a deadline to be set
 * Subclass of the Task Class.
 */
public class FixedDuration extends Task {

    protected Duration duration;

    /**
     * Constructor that takes in a description of the task and the deadline of the task.
     * @param description A String representing the task to be completed
     * @param duration A LocalDateTime representing the deadline of the task.
     */
    public FixedDuration(String description, Duration duration) {
        super(description);
        this.duration = duration;
    }

    @Override
    public String toString() {
        String days = String.format("%d Days ", duration.toDaysPart());
        String hms = String.format("%d:%02d:%02d",
                duration.toHoursPart(),
                duration.toMinutesPart(),
                duration.toSecondsPart());
        return "[F]" + super.toString() + " (for: " + days + hms + ")";
    }

}