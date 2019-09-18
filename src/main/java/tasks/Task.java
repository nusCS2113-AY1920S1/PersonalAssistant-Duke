package tasks;

import java.util.Date;

/**
 * a general Task class, to be extended
 */

public abstract class Task {

    /**
     * description of the task
     */
    protected String description;

    /**
     * status of the task
     */
    protected boolean isDone;

    /**
     * default constructor of Task
     */
    public Task(){}

    /**
     * another constructor of Task
     * @param description the description, or the content of a task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * get task's description
     * @return task's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * set the description of task
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * Set the time of task to a certain date.
     * For TODO task, this method is unneeded.
     * @param data data to set
     */
    public void setTime(Date data) {
        //for polymorphism use
    }

    /**
     * set start time of Period task
     * @param start
     *              start time
     */
    public void setStart(Date start){
    }

    /**
     * set end time of Period task
     * @param end
     *          end time
     */
    public void setEnd(Date end){
    }

    /**
     * set duration for Last task
     * @param duration
     *              duration time
     */
    public void setDuration(String duration){
    }


    /**
     * This method mark the task status as DONE.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Return the status icon.
     * @return  the status icon ("V" for done and "x" for todo) of the task
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }


    /**
     * how the task looks like in console
     * @return
     *          a string about how the task looks like
     */
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }


    /**
     * This abstract method return the String for saving the task object in txt file.
     * @return String for saving the task object in txt file
     */
    public abstract String dataString();
}
