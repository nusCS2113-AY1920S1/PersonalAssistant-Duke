package task;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Events are tasks with a start and end time.
 */
public class Event extends Task {
    protected String at;

    /**
     * task.Event Constructor
     * @param description task description
     * @param at task time period
     */
    public Event(String description, String at) {
        super(description);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmm");
        try {
            this.dateTime = sdf.parse(at);
        } catch (ParseException e) {
            System.out.println("Please enter date time format correctly: dd/mm/yyyy hhmm");
        }
        this.at = at;
    }

    /**
     * task.Event Constructor from text file
     * @param i isDone status
     * @param description of event
     * @param at event date and time
     */
    public Event (String i, String description, String at, String Snooze) {
        super(description);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmm");
        try {
            this.dateTime = sdf.parse(at);
        } catch (ParseException e) {
            System.out.println("Please enter date time format correctly: dd/mm/yyyy hhmm");
        }
        this.at = at;
        this.isDone = i.equals("1");
        this.isSnooze= Snooze.equals("1");
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + dateTime + ")";
    }

    /**
     * Returns a string that is formatted for the text file.
     * @return String
     */
    @Override
    public String toWriteFile() {
        int boolToInt = isDone ? 1 : 0;
        int snoozebooltoInt = this.isSnooze ? 1 : 0;
        return "E | " + boolToInt + " | " + this.description + " | " + this.at + " | " + snoozebooltoInt + "\n";
    }
}
