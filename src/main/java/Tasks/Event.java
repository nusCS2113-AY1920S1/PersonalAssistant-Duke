package Tasks;

/**
 * Represents a task called event.
 */
public class Event extends Task {

    private String by;
    private String start;
    private String end;

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

    @Override
    public String getType() {
        return "[E]";
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + by + " time: " + start + " to " + end + ")";
    }

    @Override
    public String getDateTime() {
        return by + " " + start + " to " + end;
    }
}