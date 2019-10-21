package Tasks;

import java.text.SimpleDateFormat;

/**
 * Represents a task called deadline.
 */
public class Deadline extends Task {

    private final String by;
    private final String time;

    /**
     * Creates a Deadline object.
     * @param description Description of a task
     * @param by Date of when a task should be done
     */
    public Deadline(String description, String by, String time) {
        super(description);
        this.by = by;
        this.time = time;
    }

    @Override
    public String toString() {
        return super.getModCode() + " " + "[D]" + super.toString() + "(by: " + getDateTime() + ")";
    }

    @Override
    public String getType() {
        return "[D]";
    }

    @Override
    public String getDateTime() {
        return by + " " + time;
    }

    @Override
    public String getDate() {
        return by;
    }

    @Override
    public String getTime() {
        return time;
    }
}