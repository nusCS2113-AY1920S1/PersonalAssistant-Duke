package duke.task;

/**
 * Represents a duke.task.
 */
public abstract class Task implements java.io.Serializable {

    protected String description;
    protected boolean isDone;

    /**
     * Constructor for Task.
     *
     * @param description the description of the Task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the description of the duke.task.
     * @return the description of the duke.task.
     */
    public boolean isDone() {
        return isDone;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Returns an icon indicating if the duke.task is done.
     * @return "✅" if the duke.task is done; otherwise "❌"
     */
    public String getStatusIcon() {
        return (isDone ? "✅" : "❌"); //return tick or X symbols
    }

    /**
     * Set the status of the duke.task.
     * @param isDone the status of the duke.task.
     */
    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Returns a summary of the duke.task.
     * @return Returns a summary of the duke.task, including description and the status icon.
     */
    @Override
    public String toString() {
        return "%1$s " + getStatusIcon() + " " + description;
    }
}
