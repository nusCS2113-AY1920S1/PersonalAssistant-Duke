package Tasks;

import Commons.DukeConstants;

/**
 * Represents a task called event.
 */
public class Event extends Assignment {

    private final String by;
    private final String start;
    private final String end;
    private static final String TIME_TO_TIME = " to ";
    private static final String TIME_DELIMITER = " to: ";
    private static final String START_OF_DATE_DELIMITER = "(at: ";
    private static final String START_OF_TIME_DELIMITER = " time: ";
    private static final String END_OF_DATE_TIME_DELIMITER = ")";
    private static final String START_DELIMITER = "Start: ";
    private static final String END_DELIMITER = "End: ";

    /**
     * Creates an Event object.
     * @param description Description of a task
     * @param by Date of when a task should be done
     * @param start Start time
     * @param end End time
     */
    public Event(String description, String by, String start, String end) {
        super(description);
        this.by = by;
        this.start = start;
        this.end = end;
    }

    /**
     * This method gets type of task.
     */
    @Override
    public String getType() {
        return DukeConstants.EVENT_INDICATOR;
    }

    /**
     * This method converts tasks to string for storage purposes.
     */
    @Override
    public String toString() {
        return super.getModCode() + DukeConstants.BLANK_SPACE
                + getType() + super.toString()
                + START_OF_DATE_DELIMITER
                + by + START_OF_TIME_DELIMITER
                + start + TIME_DELIMITER
                + end + END_OF_DATE_TIME_DELIMITER;
    }

    /**
     * This method gets date and time of task.
     */
    @Override
    public String getDateTime() {
        return by + DukeConstants.BLANK_SPACE + start + TIME_TO_TIME + end;
    }

    /**
     * This method gets start and end time for display.
     */
    @Override
    public String toShow() {
        return START_DELIMITER + start + "\n" + END_DELIMITER + end + "\n";
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
        return start + TIME_TO_TIME + end;
    }

    /**
     * This method gets start time of task.
     */
    @Override
    public String getStartTime() {
        return start;
    }

    /**
     * This method gets end time of task.
     */
    @Override
    public String getEndTime() {
        return end;
    }


}
