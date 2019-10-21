package duke.task;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

/**
 * Subclass of duke.task.Task
 * Describes an event which is classified as a deadline
 */
public class Deadline extends Task {
    private LocalDate datetime;

    /**
     * Constructor of deadline
     * Creates an instance of a deadline object by taking in its description
     * and the date which it should be done by
     *
     * @param description the description of the deadline
     * @param by          the date and time which the deadline should be done by
     */
    public Deadline(String description, Optional<String> filter, LocalDate by, String recurrencePeriod) {
        super(description, filter, recurrencePeriod);
        this.datetime = by;
        this.key = "[D]";
    }

    public Deadline(String description, Optional<String> filter, LocalDate by) {
        this(description, filter, by, "none");
    }

    public LocalDate getDatetime() {
        return this.datetime;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + datetime + ")";
    }
}
