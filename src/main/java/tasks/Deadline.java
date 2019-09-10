package tasks;

/**
 * Deadline is a public class that inherits from abstract class Task
 * A Deadline object encapsulates the String that express deadline date
 */
public class Deadline extends Task {

    protected String by;

    /**
     * This is a constructor for Deadline object
     * @param description the description of the task
     * @param by the string that represents the deadline date
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        super.type = "D";
    }

    /**
     * this function overrides the toString() function in Task to represents the full description of a Deadline object
     * @return <code>"[D]" + super.toString() + " (by: " + by + ")"</code>
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }


}
