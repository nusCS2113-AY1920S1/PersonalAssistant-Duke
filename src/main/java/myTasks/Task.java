package myTasks;

import Exception.DukeException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Task class
 * Base class which determines what kind of data a Task should hold
 * @author Kane Quah
 * @version 1.0
 * @since 08/2019
 */
public class Task {
    String description;
    boolean isDone;
    //protected String dueDate;
    private Date dueDate = null;
    protected static DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HHmm");

    /**
     * Task initialization with string as input
     * @param description String containing description information
     */
    Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Empty Task
     */
    Task(){
    }

    /**
     * Get the prerequisite of the task as defined by the user
     * To be overwritten by the After, Recurring, Within and Duration class
     */
    public String getAfter() {
        return "";
    }

    /**
     * Attempts to parse date and input it as Date type
     * @param date String which is in Date format
     * @throws DukeException DukeException thrown when dateFormatter parsing fails
     */
    void readDate(String date) throws DukeException {
        try {
            this.dueDate = dateFormatter.parse(date);
        }
        catch(ParseException e)
        {
            throw new DukeException("Error! Please enter date in the format DD-MM-YYYY 2359.");
            //throw new DukeException("Please use DDD format for date");
        }
    }

    /**
     * Returns status icon
     * @return String which is a status icon in unicode format
     */
    String getStatusIcon() {
        return (isDone ? "Y" : "N"); //return Y or N
    }

    /**
     * Marks Task as Done, if Task is already done, throw exception
     * @throws DukeException DukeException warns the use that the Task has already been done
     */
    void markDone() throws DukeException {
        if(this.isDone){
            throw new DukeException("But good sir, this task is already done!");
        }
        else
            this.isDone = true;
    }

    /**
     * Returns true/false type based on whether Task has been marked done
     * @return boolean describing if Task is Done
     */
    public boolean checkCompletion() {return this.isDone;}

    /**
     * Returns description
     * @return String description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns converted Date type into String
     * @return String Date as per Date format
     */
    public String getDueDate() {
        if(this.dueDate != null)
            return dateFormatter.format(this.dueDate);
        else
            return ""; }

    /**
     * Returns Task in print friendly format
     * @return String which contains Task Type icon, status and Description and DueDate if any
     */
    public String toList(){
        return "[?][" + this.getStatusIcon() + "] " + this.getDescription();
    }

    /**
     * Returns type of Task
     * @return String consisting of a single Letter (for now)
     */
    public String getType(){ return "G";}
}
