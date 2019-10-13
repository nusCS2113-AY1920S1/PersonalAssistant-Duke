package duke.task;

import duke.Duke;
import java.util.Date;

/**
 * Represents a general Task to be added by {@link Duke}.
 */
public abstract class Task {

    private String description;
    private boolean isDone;

    /**
     * The constructor method for Task.
     * @param description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public abstract void setNewDate(String date);

    public abstract Date getCurrentDate();

    public abstract String printInFile();

    /**
     * Returns a String representation of the status icon, indicating whether the {@link Task} was done.
     * @return a tick or a cross
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    /**
     * Returns the String description of the {@link Task}.
     * @return String description of the Task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Used to mark the {@link Task} as finished.
     */
    public void markAsDone() {
        isDone = true;
    }

    public String toString() {
        return "[" + getStatusIcon() + "] " + getDescription();
    }

    /**
     * Returns a boolean indicating whether the {@link Task} was completed.
     * @return boolean true if the task was marked as done, false otherwise
     */
    public boolean isDone() {
        return isDone;
    }
}