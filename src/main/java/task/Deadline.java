package task;
import process.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Represents a Deadline-typed-task
 */
public class Deadline extends Task {
    protected Date by;
    DateFormat fmt = new SimpleDateFormat("dd MMMM yyyy, h:mm a", Locale.US);
    /**
     * Creates a Deadline object with the given description, datetime and whether it is done
     * @param description of the task
     * @param by the due datetime of the deadline
     * @param b if the task is checked
     * @throws DukeException if the deadline is not valid
     */
    public Deadline(String description, String by,boolean b) throws DukeException {
        super(description, b);
        if (by.isBlank()) {
            throw new DukeException("blank by");
        } try {
            DatetimeFormatter.check(by);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy hhmm");
            by = by.trim();
            Date date = sdf.parse(by);
            this.by = date;
            super.tt = "D";
            super.extra = by;
        } catch (ParseException e) {
            throw new DukeException("datetime");
        }
    }

    /**
     * Represents the object of this class as a string
     * @return that string
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " +  DatetimeFormatter.view(this.fmt.format(by)) + ")";
    }
}