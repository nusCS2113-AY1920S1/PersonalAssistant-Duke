package duke.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import duke.parser.TimeParser;

import java.util.Date;

/**
 * Represents a Deadline . An Deadline is a task with a due time.
 */
public class Deadline extends Task {

    protected Date by;

    /**
     * Constructor for Deadline.
     *
     * @param description the description of the Deadline.
     * @param by          the due date of the Deadline.
     */
    public Deadline(@JsonProperty("description") String description, @JsonProperty("by") Date by) {
        super(description);
        this.by = by;
    }

    public Date getBy() {
        return by;
    }

    /**
     * Returns a summary of the deadline.
     * @return Returns a summary of the deadline, including the description, the status icon, and the due time.
     */
    @Override
    public String toString() {
        return String.format(" by %s ", TimeParser.convertDateToString(by));
    }

    public void setTime(Date by) {
        this.by = by;
    }

}
