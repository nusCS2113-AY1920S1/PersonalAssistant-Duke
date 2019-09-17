package duke.task;

/**
 * Represents a <code>Task</code> object. An <code>Event</code>
 * object is a type of <code>Task</code>.
 */
public class Event extends Task {

    private String startTiming;
    private String endTiming;

    /**
     * Constructor for <code>Event</code>.
     * @param description Details of the <code>Task</code>.
     * @param startTiming Starting date and time of the <code>Event</code>.
     * @param endTiming Ending date and time of the Event.
     */
    public Event(String description, String startTiming, String endTiming) {
        super(description);
        this.startTiming = startTiming;
        this.endTiming = endTiming;
    }

    public String getStartTiming() {
        return startTiming;
    }

    public String getEndTiming() {
        return endTiming;
    }

    /**
     * Customises the <code>toString()</code> method to print
     * the <code>Event</code> object.
     * @return Customised String for <code>Event</code> object.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startTiming + " to: " + endTiming + ")";
    }
}
