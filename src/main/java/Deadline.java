import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;

/**
 * Represents a Deadline task. Stores a description, date and whether it is done.
 */

public class Deadline extends Task {
    /**
     * Constructor for a new Deadline. Called when a Deadline is first created.
     * @param description a String description of the Deadline.
     * @param by a String done-by date
     * @throws ParseException if date does not follow the required format
     */
    public Deadline(String description, String by) throws ParseException {
        super(description);
        this.date = stringToDate(by);
        this.type = 'D';
    }

    /**
     * Constructor for an existing Deadline from our saved tasks. Called to
     * create saved tasks.
     * @param description a String description of the Deadline.
     * @param by a String done-by date
     * @param isDone a Boolean indicating whether the task has been fulfilled.
     * @throws ParseException if date does not follow the required format
     */
    public Deadline(String description, String by, boolean isDone) throws ParseException {
        super(description, isDone);
        this.date = stringToDate(by);
        this.type = 'D';
    }

    /**
     * Converts the stored Date of the Deadline to a readable String for output to the CLI.
     * @return a String version of the stored Date.
     */
    @Override
    public String toString() {
        return super.toString() + " (by: " + getDate() + ")";
    }
}