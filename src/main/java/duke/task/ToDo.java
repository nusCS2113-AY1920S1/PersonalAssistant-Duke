package duke.task;

/**
 * Represents a to do task.
 */
public class ToDo extends Task {
    /**
     * Takes in the task to be completed.
     * @param description String representing the task to be completed
     */
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}