import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a general Task to be added by {@link Duke}
 */
public abstract class Task {
    private String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns a String representation of the status icon, tick or cross, indicating whether the {@link Task} was done
     * @return a String representation of a tick or a cross, representing a done, respectively not yet finished {@link Task}
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    /**
     * Returns the String description of the {@link Task}
     * @return String description of the Task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Used to mark the {@link Task} as finished
     */
    public void markAsDone() {
        isDone = true;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + getDescription();
    }

    public abstract String printInFile();

    /**
     * Returns a boolean indicating whether the {@link Task} was completed
     * @return boolean true if the task was marked as done, false otherwise
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the suffix to be used after the days in the Date, usefull for printing the Date in the desired format
     * @param n indication the Day of the month
     * @return the suffix accordingly to the day of the month needed
     */
    static String getDaySuffix(int n) {
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    /**
     * Returns a {@link Date} instance representation of a String in the format "dd/MM/yyyy hhmm" or "dd/MM/yyyy"
     * @param date String in the format "dd/MM/yyyy hhmm" or "dd/MM/yyyy", used to extract a {@link Date} instance from
     * @return the {@link Date} instance created from the argument string
     */
    static Date getDate(String date) {
        DateFormat dateFormat = (date.length() > 11) ? new SimpleDateFormat("dd/MM/yyyy hhmm") : new SimpleDateFormat("dd/MM/yyyy");
        try {
             dateFormat.parse(date);
            return dateFormat.parse(date);
        } catch (ParseException e) {
            //case the date was not valid!
        }
        return null;
    }
}