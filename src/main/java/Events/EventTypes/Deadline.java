package Events.EventTypes;

import Events.Formatting.DateObj;

/**
 * Model_Class.Deadline object inherits Model_Class.Task.
 * Is a type of task available for use.
 */
public class Deadline extends Task {
    /**
     * Contains the date and time in a DateObj.
     */
    protected DateObj dateObj;

    /**
     * Creates deadline.
     *
     * @param description Description of task.
     * @param date        Deadline date and time.
     */
    public Deadline(String description, String date) {
        super(description);
        this.dateObj = new DateObj(date);
    }

    /**
     * Creates deadline with boolean attached, so as to read from file correctly.
     *
     * @param description Description of task.
     * @param date        Deadline date and time.
     * @param isDone      Boolean defining if the task is completed or not.
     */
    public Deadline(String description, String date, boolean isDone) {
        super(description, isDone);
        this.dateObj = new DateObj(date);
    }

    /**
     * Converts deadline type task to string format for printing.
     *
     * @return Formatted string representing the deadline and its date.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by: " + dateObj.toOutputString() + ")";
    }

    public String getDate() {
        return dateObj.toOutputString();
    }
}