import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;

/**
 * Represents an Event task. Stores a description, date and whether it is done.
 */

public class Event extends Task {
    /**
     * Constructor for a new Event. Called when an Event is first created.
     * @param description a String description of the Event.
     * @param at the date of the Event in String form
     * @throws ParseException if date does not follow the required format
     */
    public Event(String description, String at) throws ParseException {
        super(description);
        this.date = stringToDate(at);
        this.type = 'E';
    }

    /**
     * Constructor for an existing Event from our saved tasks. Called to
     * create saved tasks.
     * @param description a String description of the Event.
     * @param at the date of the Event in String form
     * @param isDone a Boolean indicating whether the task has been fulfilled.
     * @throws ParseException if date does not follow the required format
     */
    public Event(String description, String at, boolean isDone) throws ParseException {
        super(description, isDone);
        this.date = stringToDate(at);
        this.type = 'E';
    }

    /**
     * Converts the stored Date of the Event to a readable String for output to the CLI.
     * @return a String version of the stored Date.
     */
    @Override
    public String toString() {
        return super.toString() + " (at: " + getDate() + ")";
    }
}