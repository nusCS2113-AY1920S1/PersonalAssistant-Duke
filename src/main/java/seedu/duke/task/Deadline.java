package seedu.duke.task;

import java.util.Calendar;
import java.util.Date;

/**
 * Deadline is a type of task with a date/time which is the deadline time.
 */
public class Deadline extends Task {
    private Date time;

    /**
     * Instantiates the Deadline with the name and the time. Time must be in during the instantiation as it
     * cannot be changed later.
     *
     * @param name name of the Deadline
     * @param time time of the Deadline
     */
    public Deadline(String name, Date time) {
        super(name);
        this.time = time;
        this.taskType = TaskType.Deadline;
    }

    /**
     * Converts the Deadline to a human readable string containing important information about the Deadline,
     * including the type and time of this Deadline.
     *
     * @return a human readable string containing the important information
     */
    @Override
    public String toString() {
        return "[D]" + this.getStatus() + " (by: " + formatDate() + ")";
    }

    /**
     * Outputs a string with all the information of this Deadline to be stored in a file for future usage.
     *
     * @return a string containing all information of this Deadline
     */
    @Override
    public String toFileString() {
        return (this.isDone ? "1" : "0") + " deadline " + this.name + " /by "
                + formatDate();
    }

    /**
     * Outputs a formatted string of the time of this Deadline. The format is the same as input format and is
     * shared by all tasks.
     *
     * @return a formatted string of the time of this Deadline
     */
    protected String formatDate() {
        return format.format(this.time);
    }

    /**
     * Calculates whether the time set for the deadline is near enough.
     *
     * @param dayLimit maximum number of days from now for the deadline to be considered as near
     * @return the flag whether the deadline is near enough
     */
    @Override
    public boolean isNear(int dayLimit) {
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        if (this.time.compareTo(now) >= 0) {
            c.add(Calendar.DATE, dayLimit);
            if (this.time.compareTo(c.getTime()) <= 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void snooze() {
        Calendar date = Calendar.getInstance();
        date.setTime(time);
        date.add(Calendar.DAY_OF_MONTH, 3);
        time.setTime(date.getTimeInMillis());
    }
}
