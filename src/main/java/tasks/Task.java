package tasks;

import java.util.Date;

public abstract class Task {

    protected String description;
    protected boolean isDone;

    public Task(){}

    /**
     * The Task object is an abstraction of task.
     * @param description the description, or the content of a task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }


    public String getDescription() {
        return description;
    }

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

    public void setStart(Date start){
    }

    public void setEnd(Date end){
    }

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


    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }


    /**
     * This abstract method return the String for saving the task object in txt file.
     * @return String for saving the task object in txt file
     */
    public abstract String dataString();
}
