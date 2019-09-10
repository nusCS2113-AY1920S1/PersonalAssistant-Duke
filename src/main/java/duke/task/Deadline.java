package duke.task;

/**
 * Represents a <code>Task</code> object. A <code>Deadline</code>
 * object is a type of <code>Task</code>.
 */
public class Deadline extends Task {

    protected String by;

    /**
     * Constructor for <code>Deadline</code>.
     * @param description Details of the <code>Task</code>.
     * @param by Due date and time of the <code>Deadline</code>.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Customises the <code>toString()</code> method to print
     * the <code>Deadline</code> object.
     * @return Customised String for <code>Deadline</code> object.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}