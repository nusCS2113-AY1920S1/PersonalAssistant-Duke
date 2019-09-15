package duke.task;

/**
 * Represents a do After that stores description only if task is Done.
 */
public class DoAfter extends Task {

    protected String after;

    public DoAfter (String description, String after) {
        super(description);
        this.after = after;
    }

    @Override
    public String toString() {
        return "[A]" + super.toString() + " (Do after: " + after + ")";
    }
}
