package Tasks;

/**
 * Represents a task called deadline.
 */
public class Deadline extends Task {

    private String by;
    private String modCode;

    /**
     * Creates a Deadline object.
     * @param description Description of a task
     * @param by Date of when a task should be done
     */
    public Deadline(String description, String by,String modCode) {
        super(description);
        this.by = by;
        this.modCode = modCode;
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

    @Override
    public String getModCode() {
        return modCode;
    }

}