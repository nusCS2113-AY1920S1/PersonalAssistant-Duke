package Tasks;

import Commons.DukeConstants;

/**
 * Represents a task called deadline.
 */
public class Deadline extends Assignment {

    private final String by;
    private final String time;
    private static final String DEADLINE_DATE_SEPARATOR= "(by: ";
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
        return super.getModCode() + DukeConstants.BLANK_SPACE + getType() + super.toString() + DEADLINE_DATE_SEPARATOR  + getDateTime() + DEADLINE_STRING_CLOSER;
    }
    /**
     * This method returns type of task.
     */
    @Override
    public String getType() {
        return DukeConstants.DEADLINE_INDICATOR;
    }
    /**
     * This method returns date and time of task.
     */
    @Override
    public String getDateTime() {
        return by + DukeConstants.BLANK_SPACE + time;
    }
    /**
     * This method returns date of task.
     */
    @Override
    public String getDate() {
        return by;
    }
    /**
     * This method returns time of task.
     */
    @Override
    public String getTime() {
        return time;
    }
    /**
     * This method returns start time of task.
     */
    @Override
    public String getStartTime() {
        return time;
    }
    /**
     * This method returns end time of task.
     */
    @Override
    public String getEndTime() {
        return time;
    }
}