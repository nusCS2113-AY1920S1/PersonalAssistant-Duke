package duke.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task that allows for a deadline to be set.
 */
public class Deadline extends Task {

    protected LocalDateTime by;

    /**
     * Takes in a description of the task and the deadline of the task.
     * @param description A String representing the task to be completed
     * @param by A LocalDateTime representing the deadline of the task.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + dateToString(by) + ")";
    }

    /**
     * Converts the input LocalDateTime to printable format in String.
     * @param dateTime LocalDateTime object to be converted to String
     * @return String format of the LocalDateTime
     */
    private String dateToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        return dateTime.format(formatter);
    }

    public void setBy(LocalDateTime byValue) {
        by = byValue;
    }
}