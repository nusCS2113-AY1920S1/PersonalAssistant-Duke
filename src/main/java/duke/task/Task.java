package duke.task;

import duke.Duke;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a general Task to be added by {@link Duke}
 */
public abstract class Task {
    private String description;
    private boolean isDone;
    private Date date;
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public abstract void setNewDate(String date);
    public abstract Date getCurrentDate();
    /**
     * Returns a String representation of the status icon, tick or cross, indicating whether the {@link Task} was done
     * @return a String representation of a tick or a cross, representing a done, respectively not yet finished {@link Task}
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    /**
     * Returns the String description of the {@link Task}
     * @return String description of the Task
     */
    public String getDescription() {
        return description;
    }


    /**
     * Used to mark the {@link Task} as finished
     */
    public void markAsDone() {
        isDone = true;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + getDescription();
    }

    public abstract String printInFile();

    /**
     * Returns a boolean indicating whether the {@link Task} was completed
     * @return boolean true if the task was marked as done, false otherwise
     */
    public boolean isDone() {
        return isDone;
    }


}