package Tasks;

/**
 * Represents a task called deadline.
 */
public class Deadline extends Task {

    private final String by;

    /**
     * Creates a Deadline object.
     * @param description Description of a task
     * @param by Date of when a task should be done
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return super.getModCode() + " " + "[D]" + super.toString() + "(by: " + by + ")";
    }

    @Override
    public String getType() {
        return "[D]";
    }

    @Override
    public String getDateTime() {
        return by;
    }
}