package duke.tasks;

/**
 * Event is a public class that inherits form abstract class Task
 * A Event object encapsulates the String that expresses the duration of the event
 */
public class Event extends Task {
    protected String duration;

    /**
     * This is a constructor for Event object
     * @param description the description of the event
     * @param duration the string that represents the duration of the event object
     */
    public Event(String description, String duration) {
        super(description);
        this.duration = duration;
        super.type = "E";
    }

    /**
     * this function overrides the toString() function in Task to represetns the full description of an Event object
     * @return <code>"[E]" + super.toString() + " (at: " + duration + ")"</code>
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + duration + ")";
    }
}