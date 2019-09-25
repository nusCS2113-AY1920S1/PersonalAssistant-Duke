package Model_Classes;

/**
 * Parent class for all other types of tasks
 */
public class Task{
    private String description;
    private boolean isDone;

    /**
     * Constructor for the task object. takes in the description of the task
     * @param description Description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * returns whether the task has been done
     * @return isDone The state of completion of the task.
     */
    public boolean getDone() {
        return isDone;
    }

    /**
     * Returns the status of the completion of the task.
     * shows a tick if done, and a cross if not done.
     * @return A String showing a tick or X symbol.
     */
    public String getStatusIcon() {
        return (isDone ? "[\u2713] " : "[\u2718] "); //return tick or X symbols
    }

    /**
     * Returns the description of the task
     * @return description Description of the task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the task to be done
     */
    public void setDone() {
        isDone = true;
    }

    /**
     * Returns both the status icon and the description of the task.
     * @return
     */
    public String toString() {
        return getStatusIcon() + getDescription();
    }

}
