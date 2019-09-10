import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This is a class that inherits from the Task class.
 * In addition to its parent's methods, it also has the ability to parse the input date.
 *
 * @author Lee Zhen Yu
 * @version %I%
 * @since 1.0
 */
public class Deadline extends Task {

    //deadline do homework /by no idea :-p
    //[D][✗] do homework (by: no idea :-p)
    //Deadline reads in an input, separate via a /by
    protected String by;

    /**
     * Constructor of the deadline class requires a description and a date for a task to be done by
     * These are specific to every deadline object
     *
     * @param description The description of the task.
     * @param by When the task is to be done by. This is in the format "dd-MM-yyyy HHmm"
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * A method to return the date and time of a deadline of a task.
     * It reads in the date as dd-MM-yyyy HHmm and is converted into a gregorian calender object.
     * From this gregorian calender object we can easily extract the exact date and time of the deadline.
     * This raw date and time requires minor adjustments to be readable to the average user
     *
     * @return The date and time of the deadline in a format familiar to the user
     */

    //Format of deadline dd-MM-yyyy HHmm
    //Method to display deadline nicely when list is called
    //The deadline in save.txt remains in the format above
    public String getDeadline() {
        String[] month = { "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December" };

        String[] amPm = { "AM", "PM" };
        String niceDate = "";
        int hour;
        try {
            //This provides the pattern of the date input
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HHmm");
            //This reads the date input in the format given
            Date newDate = dateFormatter.parse(this.by);
            //Creates a gregorian calender
            GregorianCalendar gcal = new GregorianCalendar();
            //Converts the date to a gregorian calender for easy usage
            gcal.setTime(newDate);

            //Calender HOUR method will return 0 if its 12, have to manually change
            if (gcal.get(Calendar.HOUR) == 0) {
                hour = 12;
            }
            else {
                hour = gcal.get(Calendar.HOUR);
            }

            niceDate = String.format("%d %s %d, %02d:%02d %s",
                    gcal.get(Calendar.DATE),
                    month[gcal.get(Calendar.MONTH)],
                    gcal.get(Calendar.YEAR),
                    hour,
                    gcal.get(Calendar.MINUTE),
                    amPm[gcal.get(Calendar.AM_PM)]);
        }

        catch (ParseException e) {
            System.out.println("Error. Please enter date in the format DD-MM-YYYY 2359.");
        }
        return niceDate;
    }

    /**
     * A method to return the type of task of this object.
     *
     * @return This will return [D] to show that this task is a deadline task.
     */
    public String getType() {
        return "[D]";
    }

    /**
     * A method to return the deadline of the task as given in the user input.
     *
     * @return A string containing the user input of the deadline.
     */
    public String getBy() {
        return this.by;
    }

    /**
     * A method to return the entire task status, including its status, task type, deadline and description
     *
     * @return It returns everything about a task in a formatted string that is easily understood by the user
     */
    public String getStatusIcon() {
        return "[D]" + "[" + (isDone ? "Y" : "N") + "] " + this.description + " (by: " + this.getDeadline() + ")";
    }
}
