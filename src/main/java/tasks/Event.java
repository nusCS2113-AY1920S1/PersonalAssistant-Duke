package tasks;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * a task has exact happened time
 * command is "add event [description] /at [time]"
 */

public class Event extends Task {

    /**
     * time of this event
     */
    private Date at;

    /**
     * default constructor of Event
     */
    public Event() {
    }


    public Event(String description, Date at) {
        super(description);
        this.at = at;
    }

    @Override
    public void setTime(Date at) {
        this.at = at;
    }

    public Date getTime() {
        return at;
    }

    @Override
    /**
     * Format the output of the task list
     * @return a string which will be printed out in console when listing this event
     */
    public String toString() {
        return "[E]"
                + super.toString()
                + " (at: " + at + ")"
                + (isRecurring ? " recurring: "
                + this.recurringWeeks
                + " weeks" : "");
    }

    @Override
    /**
     * a method to format the data list data store in file
     * @return a string which will show in data file that store the task list
     */
    public String dataString() {
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hhmm");
        return "E | "
                + (this.isDone ? 1 : 0)
                + " | " + this.description
                + " | " + getPrecondition()
                + " | " + ft.format(this.at);
    }

    /**
     * @author Justin Chia
     * Getter for date object
     * @return the request date object
     */
    public Date getAt() {
        return this.at;
    }

}
