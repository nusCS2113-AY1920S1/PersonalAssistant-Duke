package duke.task;
/**
 * Represents a <code>Task</code> object. An <code>Event</code>
 * object is a type of <code>Task</code>.
 */
public class Event extends Task {

    protected String timing;
    /**
     * Constructor for <code>Event</code>.
     * @param description Details of the <code>Task</code>.
     * @param timing Date and time of the <code>Event</code>.
     */
    public Event(String description, String timing) {
        super(description);
        this.timing = timing;
    }
    /**
     * Customises the <code>toString()</code> method to print
     * the <code>Event</code> object.
     * @return Customised String for <code>Event</code> object.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + "(at: " + timing + ")";
    }
}
