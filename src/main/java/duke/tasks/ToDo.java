package duke.tasks;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Task is a public class that extends from Task.
 */
public class ToDo extends Task {

    protected int duration;

    /**
     * Primary constructor of ToDo object.
     * @param description the description of the todo object
     */
    public ToDo(String description) {
        super(description);
        super.type = "T";
        this.datetime = null;
        this.end = null;
    }

    /**
     * Secondary constructor of ToDo object with period.
     * @param description description embedded in object
     * @param inputStart start of period of task
     * @param inputEnd end of period of task
     */
    public ToDo(String description, String inputStart, String inputEnd) {
        super(description);
        SimpleDateFormat dateparser = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date date;
        try {
            date = dateparser.parse(inputStart);
            this.datetime.setTime(date);
            date = dateparser.parse(inputEnd);
            this.end.setTime(date);
        } catch (ParseException e) {
            SimpleDateFormat altparser = new SimpleDateFormat("dd MMMM yyyy hh.mm a");
            try {
                date = altparser.parse(inputStart);
                this.datetime.setTime(date);
                date = altparser.parse(inputEnd);
                this.end.setTime(date);
            } catch (ParseException f) {
                this.datetime = null;
                this.end = null;
            }
        }
        if (this.datetime.after(this.end)) {
            Calendar temp = this.datetime;
            this.datetime = this.end;
            this.end = temp;
        }
        super.type = "T";
        subtypes += "P";
    }

    /**
     * Tertiary constructor of ToDo object with fixedDuration.
     * @param description description embedded in object
     * @param duration duration of task
     */
    public ToDo(String description, String duration) {
        super(description);
        this.duration = Integer.parseInt(duration);
        super.type = "T";
        subtypes += "F";
        this.datetime = null;
        this.end = null;
    }

    /**
     * this function overrides the toString() function in Task to represents the full description of a ToDo object.
     * @return <code>"[T]" + super.toString()</code>
     *         <code>"[T]" + super.toString() + "(From: " + dateFormat.format(datetime.getTime())
     *               + " to " + dateFormat.format(datetime2.getTime()) + ")"</code>
     */
    @Override
    public String toString() {
        String text = "";
        if (subtypes.trim().isEmpty()) {
            text += "[T]" + super.toString() + "";
        } else if (subtypes.contains("P")) {
            DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy hh.mm a");
            text +=  "[T]" + super.toString() + "(From: " + dateFormat.format(this.datetime.getTime())
                    + " to " + dateFormat.format(this.end.getTime()) + ")";
        } else if (subtypes.contains("F")) {
            text += "[T]" + super.toString() + " (needs: " + this.duration + ")";
        }
        for (int i = 0; i < this.doAfter.size(); i += 1) {
            text += "\n";
            text += this.doAfter.get(i).toString();
        }
        return text;
    }
}
