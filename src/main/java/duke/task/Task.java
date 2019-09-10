package duke.task;

/**
 * A generic task, which can be marked as done and has basic functions.
 */
public class Task {
    private final String description;
    boolean done;

    /**
     * Initializes a task not yet done with the given description.
     *
     * @param description A description of this task.
     */
    Task(String description) {
        this.description = description;
        this.done = false;
    }

    /**
     * Marks the task as done.
     */
    public void markDone() {
        done = true;
    }

    /**
     * Gets a boolean equivalent of whether this task is done.
     *
     * @return true if this task is done and false when it is not done.
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Gets a character representing whether this task is done.
     *
     * @return tick if this task is done and cross when it is not done.
     */
    private String getDoneChar() {
        return done ? "✓" : "✘";
    }

    /**
     * Returns the description associated with this task.
     *
     * @return This task's description.
     */
    String getDescription() {
        return this.description;
    }

    /**
     * Returns a string representation of this task.
     *
     * @return The desired string representation based on specifications.
     */
    @Override
    public String toString() {
        String boxedChar = "[" + getDoneChar() + "]";
        return boxedChar + " " + description;
    }

    /**
     * Exports this task in string format.
     * It is human readable and easily portable for other applications.
     *
     * @return A string representation of this task containing its done status (0 or 1)
     *     and its description.
     */
    public String export() {
        return (done ? "1 | " : "0 | ");
    }
}
