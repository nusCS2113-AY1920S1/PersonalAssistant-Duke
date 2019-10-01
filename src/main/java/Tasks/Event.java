package Tasks;

/**
 * Represents a task called event.
 */
public class Event extends Task {

    private String by;
    private String start;
    private String end;

    @Override
    public String getType() {
        return "[E]";
    }

    /**
     * Creates an Event object.
     * @param description Description of a task
     * @param by Date of task
     * @param start Time of when a task starts
     * @param end Time of when a task ends
     */
    public Event(String description, String by, String start, String end) {
        super(description);
        this.by = by;
        this.start = start;
        this.end = end;
    }

    /**
     * Converts the Event object to a string.
     * @return This returns the string of the Event object
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + by + " time: " + start + " to " + end + ")";
    }

    @Override
    public String getDateTime() {
        return by + " " + start + " to " + end;
    }
}