package duke.tasks;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Deadline is a public class that inherits from abstract class Task.
 * A Deadline object encapsulates the String that express deadline date
 */
public class Deadline extends Task {

    protected String by;

    /**
     * This is a constructor for Deadline object.
     * @param description the description of the task
     * @param by the string that represents the deadline date
     * @param recurringDuration frequency of Deadline task. 0 days implies a one time event only
     */
    public Deadline(String description, String by, Period recurringDuration) {
        super(description, recurringDuration);
        SimpleDateFormat dateparser = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date date;
        try {
            date = dateparser.parse(by);
            this.datetime.setTime(date);
            this.end = this.datetime;
        } catch (ParseException e) {
            SimpleDateFormat altparser = new SimpleDateFormat("dd MMMM yyyy hh.mm a");
            try {
                date = altparser.parse(by);
                this.datetime.setTime(date);
                this.end =  this.datetime;
            } catch (ParseException f) {
                datetime = null;
            }
        }
        this.by = by;
        super.type = "D";
    }

    public Deadline(String description, String by) {
        this(description, by, Period.of(0,0,0));
    }

    /**
     * this function overrides the toString() function in Task to represents the full description of a Deadline object.
     * @return <code>"[D]" + super.toString() + " (by: " + by + ")"</code>
     */
    @Override


    public String toString() {
        String text = "";
        if (datetime != null) {
            DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy hh.mm a");
            text += "[D]" + super.toString() + " (by: " + dateFormat.format(datetime.getTime()) + ")";
        } else {
            text += "[D]" + super.toString() + " (by: " + by + ")";
        }
        // recurring event
        if (!this.recurringDuration.isZero()) {
            text += "Recurring event, once every ";
            text += String.format("%d years, %d months, %d days", recurringDuration.getYears(),
                    recurringDuration.getMonths(), recurringDuration.getDays()) + "\n";
        }

        for (int i = 0; i < this.doAfter.size(); i += 1) {
            text += "\n";
            text += this.doAfter.get(i).toString();
        }


        return text;
    }


}
