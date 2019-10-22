package tasks;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * a task has deadline
 * command is "add deadline [description] /by [deadline time]"
 */

public class Deadline extends Task {

    public Date getTime() {
        return this.by;
    }

    protected Date by;


    /**
     * default constructor of Deadline
     */
    public Deadline() {
    }

    /**
     * another constructor of Deadline
     *
     * @param description The description, or content of deadline
     * @param by the time when this task due
     */
    public Deadline(String description, Date by) {
        super(description);
        this.by = by;
    }

    /**
     * set the deadline
     *
     * @param by the deadline of this task
     */
    public void setTime(Date by) {
        this.by = by;
    }

    @Override
    /**
     * @return string
     * how task list will print out in console
     */
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    @Override
    /**
     * a method to format the data list data store in file
     * @return string
     * a string which will show in data file that store the task list
     */
    public String dataString() {
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hhmm");
        return "D | "
                + (this.isDone ? 1 : 0) + " | "
                + this.description + " | "
                + getPrecondition()
                + " | " + ft.format(this.by);
    }
}
