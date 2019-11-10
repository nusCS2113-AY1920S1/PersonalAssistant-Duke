package Tasks;

import Commons.DukeConstants;

/**
 * Represents a task called event.
 */
public class Event extends Assignment {

    private final String by;
    private final String start;
    private final String end;

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
     * This method returns type of task.
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
        return super.getModCode() + " " + getType() + super.toString() + "(at: " + by + " time: " + start + " to: " + end + ")";
    }
    /**
     * This method returns date and time of task.
     */
    @Override
    public String getDateTime() {
        return by + " " + start + " to " + end;
    }
    /**
     * This method returns start and end time for display.
     */
    @Override
    public String toShow() {
        return "Start: " + start + "\nEnd: " + end + "\n";//
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
        return start + " to " + end;
    }
    /**
     * This method returns start time of task.
     */
    @Override
    public String getStartTime() {
        return start;
    }
    /**
     * This method returns end time of task.
     */
    @Override
    public String getEndTime() {
        return end;
    }


}
