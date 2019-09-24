package duke.tasks;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
            this.datetime.setTime(date);
            this.end.setTime(date);
        } catch (ParseException e) {
            SimpleDateFormat altparser = new SimpleDateFormat("dd MMMM yyyy hh.mm a");
            try {
                date = altparser.parse(at);
                this.datetime.setTime(date);
                this.end.setTime(date);
            } catch (ParseException f) {
                this.datetime = null;
                this.end = null;
            }
        }
        this.at = at;
        super.type = "E";
    }

    public Event(String description, String inputStart, String inputEnd) {
        super(description);
        SimpleDateFormat dateparser = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date date;
        try {
            date = dateparser.parse(inputStart);
            this.datetime.setTime(date);
            date = dateparser.parse(inputEnd);
            this.end.setTime(date);
            System.out.println("Creation");
            System.out.println(this.datetime.get(Calendar.HOUR_OF_DAY));
            System.out.println(this.end.get(Calendar.HOUR_OF_DAY));
            System.out.println(this.duration);
        } catch (ParseException e) {
            SimpleDateFormat altparser = new SimpleDateFormat("dd MMMM yyyy hh.mm a");
            try {
                date = altparser.parse(inputStart);
                this.datetime.setTime(date);
                date = altparser.parse(inputEnd);
                this.end.setTime(date);
            } catch (ParseException f) {
                System.out.println(inputStart);
                System.out.println(inputEnd);
                this.datetime = null;
                this.end = null;
            }
        }
        System.out.println(this.datetime);
        System.out.println(this.end);
        if (this.datetime.after(this.end)) {
            Calendar temp = this.datetime;
            this.datetime = this.end;
            this.end = temp;
        }
        super.type = "E";
        this.subtypes = "P";
    }

    /**
     * this function overrides the toString() function in Task to represetns the full description of an Event object.
     * @return <code>"[E]" + super.toString() + " (at: " + duration + ")"</code>
     */
    @Override
    public String toString() {
        String text = "";
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy hh.mm a");
        if (this.end != null) {
            text += "[E]" + super.toString() + " (From: " + dateFormat.format(this.datetime.getTime()) + " to " + dateFormat.format(this.end.getTime()) + ")\n";
        } else if (this.datetime != null) {
            text +=  "[E]" + super.toString() + " (at: " + dateFormat.format(this.datetime.getTime()) + ")\n";
        } else {
            text += "[E]" + super.toString() + " (at: " + at + ")\n";
        }
        for (int i = 0; i < this.doAfter.size(); i += 1) {
            text += this.doAfter.get(i).toString() + "\n";
        }
        return text;
    }
}