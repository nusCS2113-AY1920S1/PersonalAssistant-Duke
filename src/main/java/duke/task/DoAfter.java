package duke.task;
import duke.parser.Parser;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Represents a special task that {@link Task } specified by the due {@link Date}
 */
public class DoAfter extends Task{

    private String after;
    private Date date;

    /**
     * CONSTRUCTOR
     * by specifying the description of this task
     * and the description of the task
     * after which this task need to be done
     */
    public DoAfter(String description, Task todo) {
        super(description);
        this.after = todo.getDescription();
    }

    /**
     * CONSTRUCTOR
     * by specifying the description of this task
     * and the date/time after which this task need to be done
     */
    public DoAfter(String description, String after) {
        super(description);
        this.after = after;
        this.date = Parser.getDate(after);
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
        return (date == null) ? "[A]" + super.toString() + "(after: " + after + ")" : "[A]" + super.toString() + "(after: " + getDateString(date) + ")";
    }

    /**
     * Returns the {@link Date } instance as a String to be printed in the file
     * @param date deadline {@link Date} for finishing the task
     * @return String the date for the deadline
     */
    private String getDateString(Date date) {
        if (date == null)
            return after;
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String pattern = after.length() > 11 ? "d'" + Parser.getDaySuffix(localDate.getDayOfMonth()) + "' 'of' MMMM yyyy, ha " : "d'" + Parser.getDaySuffix(localDate.getDayOfMonth()) + "' 'of' MMMM yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    /**
     * Returns the String representation of the {@link Deadline} in format compatible to be easily read and written in a text file on the hard disc
     * @return String used to print the {@link Task } in the text file
     */
    public String printInFile() {
        return this.isDone() ? "A|1|" + this.getDescription() + "| after" + this.after : "A|0|" + this.getDescription() + "| after" + this.after;
    }


}
