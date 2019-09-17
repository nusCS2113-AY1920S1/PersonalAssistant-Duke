package duke.tasks;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Event is a public class that inherits form abstract class Task.
 * A Event object encapsulates the String that expresses the duration of the event
 */
public class Event extends Task {
    protected String at;

    /**
     * This is a constructor for Event object.
     * @param description the description of the event
     * @param at the string that represents the duration of the event object
     */
    public Event(String description, String at) {
        super(description);
        SimpleDateFormat dateparser = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date date;
        try {
            date = dateparser.parse(at);
            datetime.setTime(date);
        } catch (ParseException e) {
            SimpleDateFormat altparser = new SimpleDateFormat("dd MMMM yyyy hh.mm a");
            try {
                date = altparser.parse(at);
                datetime.setTime(date);
            } catch (ParseException f) {
                datetime = null;
            }
        }
        this.at = at;
        super.type = "E";
    }

    /**
     * this function overrides the toString() function in Task to represetns the full description of an Event object.
     * @return <code>"[E]" + super.toString() + " (at: " + duration + ")"</code>
     */
    @Override
    public String toString() {
        if (datetime != null) {
            DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy hh.mm a");
            return "[E]" + super.toString() + " (at: " + dateFormat.format(datetime.getTime()) + ")";
        } else {
            return "[E]" + super.toString() + " (at: " + at + ")";
        }
    }
}