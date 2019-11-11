package tasks;

import commons.DukeConstants;

/**
 * Represents a task called deadline.
 */
public class Deadline extends Assignment {

    private final String by;
    private final String time;
    private static final String DEADLINE_DATE_SEPARATOR = "(by: ";
    private static final String DEADLINE_STRING_CLOSER = ")";

    /**
     * Creates a Deadline object.
     * @param description Description of a task
     * @param by Date of when a task should be done
     */
    public Deadline(String description, String by, String time) {
        super(description);
        this.by = by;
        this.time = time;
    }

    /**
     * This method converts tasks to string for storage purposes.
     */
    @Override
    public String toString() {
        return super.getModCode()
                + DukeConstants.BLANK_SPACE + getType()
                + super.toString() + DEADLINE_DATE_SEPARATOR
                + getDateTime() + DEADLINE_STRING_CLOSER;
    }

    /**
     * This method gets type of task.
     */
    @Override
    public String getType() {
        return DukeConstants.DEADLINE_INDICATOR;
    }

    /**
     * This method gets date and time of task.
     */
    @Override
    public String getDateTime() {
        return by + DukeConstants.BLANK_SPACE + time;
    }

    /**
     * This method gets date of task.
     */
    @Override
    public String getDate() {
        return by;
    }

    /**
     * This method gets time of task.
     */
    @Override
    public String getTime() {
        return time;
    }

    /**
     * This method gets start time of task.
     */
    @Override
    public String getStartTime() {
        return time;
    }

    /**
     * This method gets end time of task.
     */
    @Override
    public String getEndTime() {
        return time;
    }
}