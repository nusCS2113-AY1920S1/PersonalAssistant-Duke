package task;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The parent Task class containing the description of the task and its done status.
 */
public class Task implements Serializable {
    protected String description;
    protected boolean isDone;
    protected String type;
    protected Date date;
    protected String after;

    private static SimpleDateFormat dataformat = new SimpleDateFormat("dd-MM-yyyy");

    /**
     * Creates a Task instance and initialises the required attributes.
     * @param description Description of the Task.
     */
    public Task(String description) {
        this.description = description;
        this.type = "T";
        this.isDone = false;
    }

    /**
     * Formats the representation of the Task to be printed.
     * @return The task's status icon, description and deadline.
     */
    public String giveTask() {
        return "[" + getStatus() + "] [" + this.type + "]" + this.description;
    }

    /**
     * Gives the status icon of the Task.
     * @return The status icon of the Task based on isDone.
     */
    public String getStatus() {
        return (isDone ? "Done" : "NotDone"); //return tick or X symbols
    }

    /**
     * Sets as done the isDone attribute.
     */
    public void setDone() {
        this.isDone = true;
    }

    /**
     * Sets as done the isDone attribute.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Sets a new value to the description attribute.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the description of the Task.
     * @return Task Description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets the type of the Task.
     * @return Task type.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Gets the date of the Task.
     * @return Task Date.
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Gets the date of the Task.
     * @return Task Date.
     */
    public String getDateStr() {
        return dataformat.format(this.date);
    }


}
