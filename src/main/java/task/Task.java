package task;

import java.io.Serializable;

/**
 * The parent Task class containing the description of the task and its done status.
 */
public class Task implements Serializable {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a Task instance and initialises the required attributes.
     * @param description Description of the Task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Formats the representation of the Task to be printed.
     * @return The task's status icon, description and deadline.
     */
    public String giveTask() {
        return "[" + getStatusIcon() + "] " + this.description;
    }

    /**
     * Gives the status icon of the Task.
     * @return The status icon of the Task based on isDone.
     */
    public String getStatusIcon() {
        return (isDone ? "✓" : "✘"); //return tick or X symbols
    }

    /**
     * Sets as done the isDone attribute.
     */
    public void setDone() {
        this.isDone = true;
    }
    public String getDescription() {
        return this.description;
    }
}
