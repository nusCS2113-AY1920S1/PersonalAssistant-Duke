package duke.tasks;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * ToDoPeriod is a public class that extends from Task
 */
public class ToDoPeriod extends Task {

    Calendar datetime2 = Calendar.getInstance();

    /**
     * Constructor for ToDoPeriod
     * @param description description of task
     * @param start start date
     * @param end end date
     */
    public ToDoPeriod(String description, String start, String end) {
        super(description);
        SimpleDateFormat dateparser = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date date;
        try {
            date = dateparser.parse(start);
            datetime.setTime(date);
            date = dateparser.parse(end);
            datetime2.setTime(date);
        } catch (ParseException e) {
            SimpleDateFormat altparser = new SimpleDateFormat("dd MMMM yyyy hh.mm a");
            try {
                date = altparser.parse(start);
                datetime.setTime(date);
                date = altparser.parse(end);
                datetime2.setTime(date);
            } catch (ParseException f) {
                datetime = null;
            }
        }
        if (datetime.after(datetime2)) {
            Calendar temp = datetime;
            datetime = datetime2;
            datetime2 = temp;
        }
        super.type = "P";
    }

    /**
     * this function overrides the toString() for ToDoPeriod object
     * @return <code>"[T]" + super.toString() + "(From: " + dateFormat.format(datetime.getTime())
     *                 + " to " + dateFormat.format(datetime2.getTime()) + ")"</code>
     */
    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy hh.mm a");
        return "[T]" + super.toString() + "(From: " + dateFormat.format(datetime.getTime())
                + " to " + dateFormat.format(datetime2.getTime()) + ")";
    }
}
