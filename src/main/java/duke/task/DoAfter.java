package duke.task;

import duke.parser.Convert;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Represents a special task that {@link Task } specified by the due {@link Date}.
 * One of the B-Extensions
 * @author VirginiaYu
 */
public class DoAfter extends Task {

    private String after;
    private Date date;

    /**
     * The constructor method of DoAfter.
     *
     * @param description by specifying the description of this task.
     * @param todo which this task need to be done.
     */
    public DoAfter(String description, Task todo) {
        super(description);
        this.after = todo.getDescription();
    }

    /**
     * The constructor method of DoAfter.
     *
     * @param description specifying the description of this task.
     * @param after the date/time after which this task need to be done.
     */
    public DoAfter(String description, String after) {
        super(description);
        this.after = after;
        this.date = Convert.stringToDate(after);
    }

    @Override
    public void setNewDate(String date) {
        //do nothing
    }

    @Override
    public Date getCurrentDate() {
        return null;
    }

    @Override
    public String toString() {
        if (date == null) {
            return "[A]" + super.toString() + "(after: " + after + ")";
        } else {
            return "[A]" + super.toString() + "(after: " + getDateString(date) + ")";
        }
    }

    /**
     * Returns the {@link Date } instance as a String to be printed in the file.
     *
     * @param date deadline {@link Date} for finishing the task
     * @return String the date for the deadline
     */
    private String getDateString(Date date) {
        if (date == null) {
            return after;
        }
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String pattern;
        if (after.length() > 11) {
            pattern = "d'" + Convert.getDaySuffix(localDate.getDayOfMonth()) + "' 'of' MMMM yyyy, ha ";
        } else {
            pattern = "d'" + Convert.getDaySuffix(localDate.getDayOfMonth()) + "' 'of' MMMM yyyy";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    /**
     * Returns the String of the {@link Deadline} in format compatible in a text file.
     *
     * @return String used to print the {@link Task } in the text file
     */
    public String printInFile() {
        if (this.isDone()) {
            return "A|1|" + this.getDescription() + "| after" + this.after;
        } else {
            return "A|0|" + this.getDescription() + "| after" + this.after;
        }
    }
}