package duke.task;

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
    public Deadline(String description, Date by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns a summary of the deadline.
     *
     * @return Returns a summary of the deadline, including the description, the status icon, and the due time.
     */
    @Override
    public String toString() {
        return String.format(super.toString(), "⏰") + String.format(" (by: %s)", TimeParser.convertDateToString(by));
    }
}
