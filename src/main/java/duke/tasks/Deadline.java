package duke.tasks;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Deadline is a public class that inherits from abstract class Task
 * A Deadline object encapsulates the String that express deadline date
 */
public class Deadline extends Task {

    protected String by;

    /**
     * This is a constructor for Deadline object
     * @param description the description of the task
     * @param by the string that represents the deadline date
     */
    public Deadline(String description, String by) {
        super(description);
        SimpleDateFormat dateparser = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date date;
        try {
            date = dateparser.parse(by);
            datetime.setTime(date);
        } catch (ParseException e) {
            SimpleDateFormat altparser = new SimpleDateFormat("dd MMMM yyyy hh.mm a");
            try {
                date = altparser.parse(by);
                datetime.setTime(date);
            } catch (ParseException f) {
                datetime = null;
            }
        }
        this.by = by;
        super.type = "D";
    }

    /**
     * this function overrides the toString() function in Task to represents the full description of a Deadline object
     * @return <code>"[D]" + super.toString() + " (by: " + by + ")"</code>
     */
    @Override
    public String toString() {
        if (datetime != null) {
            DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy hh.mm a");
            return "[D]" + super.toString() + " (by: " + dateFormat.format(datetime.getTime()) + ")";
        } else {
            return "[D]" + super.toString() + " (by: " + by + ")";
        }
    }


}
