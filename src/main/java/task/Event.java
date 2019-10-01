package task;

import exception.DukeException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Events are tasks with a start and end time.
 */
public class Event extends Task {
    protected String atStart;
    protected String atEnd;
    protected Date dateTimeStart;
    protected Date dateTimeEnd;

    /**
     * task.Event Constructor
     * @param description task description
     * @param atStart task time start
     * @param atEnd task time end
     */
    public Event(String description, String atStart, String atEnd) throws DukeException {
        super(description);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmm");
        try {
            this.dateTimeStart = sdf.parse(atStart);
            this.dateTimeEnd = sdf.parse(atEnd);
        } catch (ParseException e) {
            throw new DukeException("Please enter date time format correctly: dd/mm/yyyy hhmm");
        }
        this.atStart = atStart;
        this.atEnd = atEnd;
    }

    /**
     * task.Event Constructor from text file
     * @param i isDone status
     * @param description of event
     * @param atStart event date and time start
     * @param atEnd event date and time end
     * @param snooze snooze status
     */
    public Event(String i, String description, String atStart, String atEnd, String snooze) throws DukeException {
        super(description);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmm");
        try {
            this.dateTimeStart = sdf.parse(atStart);
            this.dateTimeEnd = sdf.parse(atEnd);
        } catch (ParseException e) {
            throw new DukeException("Please enter date time format correctly: dd/mm/yyyy hhmm");
        }
        this.atStart = atStart;
        this.atEnd = atEnd;
        this.isDone = i.equals("1");
        this.isSnooze = snooze.equals("1");
    }

    @Override
    public boolean containsDate(String s) {
        return this.atStart.contains(s);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + dateTimeStart
                + " to " + dateTimeEnd + ")";
    }

    @Override
    public Date getDateTime() {
        return this.dateTimeStart;
    }

    public Date getDateTimeStart() {
        return this.dateTimeStart;
    }

    public Date getDateTimeEnd() {
        return this.dateTimeEnd;
    }

    /**
     * Returns a string that is formatted for the text file.
     * @return String
     */
    @Override
    public String toWriteFile() {
        int boolToInt = isDone ? 1 : 0;
        int snoozebooltoInt = this.isSnooze ? 1 : 0;
        return "E | " + boolToInt + " | " + this.description + " | " + this.atStart + " | "
                + this.atEnd + " | " + snoozebooltoInt + "\n";
    }
}
