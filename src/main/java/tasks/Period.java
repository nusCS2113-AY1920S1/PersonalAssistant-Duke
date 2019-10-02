package tasks;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Provide support for managing tasks that need to be done within a certain period
 * e.g., collect certificate between Jan 15 and 25th.
 * command is "add period [description] /from [time] /to [time]"
 */

public class Period extends Task {

    /**
     * the start time of the task
     */
    Date start;

    /**
     * the end time of the task
     */
    Date end;

    /**
     * default constructor of Period
     */
    public Period() {
    }

    /**
     * another constructor of Period
     *
     * @param description the description of this task
     * @param start       the starting time of this task
     * @param end         the end time of this task
     */
    public Period(String description, Date start, Date end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    /**
     * set the start time
     *
     * @param start starting time
     */
    public void setStart(Date start) {
        this.start = start;
    }

    public Date getStart() {
        return this.start;
    }

    public Date getEnd() {
        return this.end;
    }

    /**
     * set the end time
     *
     * @param end end time
     */
    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    /**
     * a method to format the output of the task list
     * @return a string which will be printed out in console when listing this Period task
     */
    public String toString() {
        return "[P]" + super.toString() + " (from: " + start + " to: " + end + ")";
    }

    @Override
    /**
     * a method to format the data list data store in file
     * @return a string which will show in data file that store the task list
     */
    public String dataString() {
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hhmm");
        return "P | "
                + (this.isDone ? 1 : 0)
                + " | "
                + this.description
                + " | "
                + ft.format(this.start)
                + " | "
                + ft.format(this.end);
    }
}
