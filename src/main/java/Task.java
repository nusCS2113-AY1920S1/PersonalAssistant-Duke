import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;

/**
 * Represents an instance of a Task. Contains a description, the type of Task (Event, Deadline or Todo)
 * and the Date of the Task if necessary.
 */

public class Task {
    protected String description;
    protected boolean isDone;
    protected char type;
    protected Date date;

    /**
     * Constructor for a new basic Task.
     * @param description the description of the Task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Constructor for an existing saved Task.
     * @param description the description of the Task
     * @param isDone a Boolean indicating if the Task has been done.
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Returns the description of the Task.
     * @return the description of the Task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Depending on whether the Task is done, returns a tick symbol or cross symbol.
     * @return an icon, that is a tick if the Task is done and a cross if it is not.
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718");
    }

    /**
     * Sets the Task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Returns the type (Todo, Deadline or Event) of the Task.
     * @return the type of the Task.
     */
    public char getType() {
        return type;
    }

    /**
     * Returns whether the Task is done or not.
     * @return a Boolean indicating the done status of the Task
     */
    public boolean getDoneStatus() {
        return isDone;
    }

    /**
     * Converts the saved Date of the Task to a String format, and returns it.
     * @return a String version of the saved Date.
     */
    public String getDate() {
        DateFormat dayFormat = new SimpleDateFormat("d");
        int day = Integer.parseInt(dayFormat.format(date)) % 10;
        String suffix = day == 1 ? "st" : (day == 2 ? "nd" : (day == 3 ? "rd" : "th"));
        String stringDate = (new SimpleDateFormat("EEEEE, ")).format(date) + (dayFormat.format(date)) + suffix + " of " + (new SimpleDateFormat("MMMMM yyyy, hh:mm aaa")).format(date);
        return stringDate;
    }

    /**
     * Converts the saved Date of the Task to a text format. To be called when the Storage class
     * is converting existing Tasks to a text format to save into a file.
     * @return a String version of the saved Date.
     */
    public String getDateToSave() {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HHmm");
        String stringDate = format.format(date);
        return stringDate;
    }

    /**
     * Converts the stored Date of the Task to a readable String for output to the CLI.
     * @return a String version of the stored Date.
     */
    @Override
    public String toString() {
        return "[" + getType() + "][" + getStatusIcon() + "] " + description;
    }

    /**
     * Converts the input String as typed by the user into a Date to be saved.
     * @param stringDate a String version of the date we want.
     * @return a Date version of the inputted String.
     */
    public Date stringToDate(String stringDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date dateValue = formatter.parse(stringDate);
        return dateValue;
    }
}