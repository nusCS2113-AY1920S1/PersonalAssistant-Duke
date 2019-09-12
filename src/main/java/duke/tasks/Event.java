package duke.tasks;

/**
 * A class inheriting from duke.tasks.Task used to represent tasks that have both a description and an
 * associated location.
 */
public class Event extends Task {

    private String at;

    /**
     * Constructor for the duke.tasks.Event object, which consists of the description of a task and a
     * location that is associated with it.
     *
     * @param description the description of the task
     * @param at the location associated with the task
     */
    public Event(String description, String at) {
        super(description);
        this.at = at.trim();
    }

    /**
     * Returns a String representation of the duke.tasks.Event object, displaying its type (duke.tasks.Event),
     * description and the location associated with it.
     *
     * @return a String representation of the duke.tasks.Event object
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + at + ")";
    }
}