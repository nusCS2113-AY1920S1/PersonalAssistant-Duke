package duke.task;

//@@author maxxyx96
/**
 * Represents a do After that stores description only if task is Done.
 */
public class DoAfter extends Task {

    protected String after;

    /**
     * Creates a DoAfter Task with the specific description and prerequisite task.
     *
     * @param description Description of the Task.
     * @param after Prerequisite task that is to be done beforehand.
     */
    public DoAfter(String description, String after) {
        super(description);
        this.after = after;
    }

    /**
     * Extracts a task content into readable string.
     *
     * @return String to be displayed.
     */
    @Override
    public String toString() {
        return "[A]" + super.toString() + " (Do after: " + after + ")";
    }

    /**
     * Extracts a task content into readable string (GUI).
     *
     * @return String to be displayed.
     */
    @Override
    public String toStringGui() {
        return "[A]" + super.toStringGui() + " (Do after: " + after + ")";
    }

    /**
     * Extracts a task content into string that is suitable for text file.
     *
     * @return String to be written into text file.
     */
    @Override
    public String toFile() {
        return "A|" + super.toFile() + "|" + after;
    }
}
//@@author