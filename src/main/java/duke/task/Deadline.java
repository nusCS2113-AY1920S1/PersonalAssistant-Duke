package duke.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

/**
 * Subclass of duke.task.Task
 * Describes an event which is classified as a deadline
 */
public class Deadline extends Task {
    private LocalDateTime datetime;

    /**
     * Constructor of deadline
     * Creates an instance of a deadline object by taking in its description
     * and the date which it should be done by
     *
     * @param description the description of the deadline
     * @param by          the date and time which the deadline should be done by
     */
    public Deadline(String description, Optional<String> filter, String recurrencePeriod, int duration, LocalDateTime by) {
        super(description, filter, recurrencePeriod, duration);
        this.datetime = by;
        this.key = "[D]";
    }

    public LocalDateTime getDateTime() {
        return this.datetime;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " (by: " +
                datetime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + ")";
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + datetime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + ")";
    }
}
