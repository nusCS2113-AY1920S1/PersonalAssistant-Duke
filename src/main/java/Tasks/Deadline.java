package Tasks;

/**
 * Represents a task called deadline.
 */
public class Deadline extends Task {

    private String by;

    @Override
    public String getType() {
        return "[D]";
    }

    /**
     * Creates a Deadline object.
     * @param description Description of a task
     * @param by Date of when a task should be done
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Converts the Deadline object to a string.
     * @return This returns the string of the Deadline object
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    @Override
    public String getDateTime() {
        return by;
    }
}