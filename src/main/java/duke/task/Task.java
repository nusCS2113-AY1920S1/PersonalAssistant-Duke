package duke.task;

/**
 * Represents a task.  Task is an abstract class that can not be
 * instantiated
 */
public abstract class Task {

    /**
     * A String that represents the description of the task.
     */
    private String description;

    /**
     * A boolean that represents the status of the task( 1 means done, 0 means not yet).
     */
    private boolean isDone;

    /**
     * Initialises the minimum fields required to setup a Task.
     *
     * @param description A string that represents the description of certain task.
     */
    public Task(String description, String dateTime) {
        this.description = description;
        this.isDone = false;
    }

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns an icon that represents the status of the task.
     *
     * @return Tick if completed, cross if uncompleted.
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    /**
     * Check if the task isDone.
     *
     * @return boolean value of isDone
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Returns the description of the task.
     *
     * @return A string that represents the specific activity associated with
     *         the task.
     */

    public String getDescription() {
        return description;
    }

    public abstract String toString();

}