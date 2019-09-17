package duke.tasks;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Task is a public class that extends from Task
 */
public class ToDo extends Task {

    Calendar datetime2 = Calendar.getInstance();

    /**
     * Primary constructor of ToDo object
     * @param description the description of the todo object
     */
    public ToDo(String description) {
        super(description);
        super.type = "T";
    }

    /**
     * Secondary constructor of ToDo object with period
     * @param description description embedded in object
     * @param start start of period
     * @param end end of period
     */
    public ToDo(String description, String start, String end) {
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
        super.type = "T";
        subtypes += "P ";
    }

    /**
     * this function overrides the toString() function in Task to represents the full description of a ToDo object
     * @return <code>"[T]" + super.toString()</code>
     * @return <code>"[T]" + super.toString() + "(From: " + dateFormat.format(datetime.getTime())
     *               + " to " + dateFormat.format(datetime2.getTime()) + ")"</code>
     */
    @Override
    public String toString() {
        if (subtypes.isEmpty()) {
            return "[T]" + super.toString();
        }
        else if (subtypes.contains("P")){
            DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy hh.mm a");
            return "[T]" + super.toString() + "(From: " + dateFormat.format(datetime.getTime())
                    + " to " + dateFormat.format(datetime2.getTime()) + ")";
        }
        return null;
    }
}
