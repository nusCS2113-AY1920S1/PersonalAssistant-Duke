package model.task;

import utils.DukeException;

import java.util.ArrayList;
import java.util.Date;

/**
 * a general Task class, to be extended
 */

public abstract class Task {

    public static ArrayList<Task> tasks;

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
    public Task() {
        this.isDone = false;
    }

    /**
     * another constructor of Task
     *
     * @param description the description, or the content of a task
     *                    params isDone and recurring are auto set to false
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * get task's description
     *
     * @return task's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * set the description of task
     *
     * @param description the description, or the content of a task
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * Set the time of task to a certain date.
     * For TODO task, this method is unneeded.
     *
     * @param data data to set
     */
    public void setTime(Date data) {
        //for polymorphism use
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] "
                + this.description;
    }

    /**
     * set start time of Period task
     *
     * @param start start time
     */
    public void setStart(Date start) {
    }


    /**
     * set end time of Period task
     *
     * @param end end time
     */
    public void setEnd(Date end) {
    }


    /**
     * set duration for Last task
     *
     * @param duration duration time
     */
    public void setDuration(String duration) {
    }


    /**
     * Return the status icon.
     *
     * @return the status icon ("V" for done and "x" for todo) of the task
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    /**
     * This abstract method return the String for saving the task object in txt file.
     *
     * @return String for saving the task object in txt file
     */
    public abstract String dataString();


}
