package tasks;

import java.util.Date;

public abstract class Task {
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected String description;
    protected boolean isDone;

    /**
     * The Task object is an abstraction of task.
     * @param description the description, or the content of a task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Return the status icon.
     * @return  the status icon ("V" for done and "x" for todo) of the task
     */
    public String getStatusIcon() {
        return (isDone ?  "V" : "x"); //return tick or X symbols
    }

    /**
     * Set the time of task to a certain date.
     * For TODO task, this method is unneeded.
     * @param data data to set
     */
    public void setTime(Date data) {
        //for polymorphism use
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

    /**
     * This method mark the task status as DONE.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * This abstract method return the String for saving the task object in txt file.
     * @return String for saving the task object in txt file
     */
    public abstract String dataString();
}
