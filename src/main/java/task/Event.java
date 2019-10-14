package task;

import exception.DukeException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Events are tasks with a start and end time.
 */
public class Event extends Task {
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
    }

    /**
     * task.Event Constructor from text file
     * @param i isDone status
     * @param description of event
     * @param atStart event date and time start
     * @param atEnd event date and time end
     */
    public Event(String i, String description, String atStart, String atEnd) throws DukeException {
        super(description);
        this.dateTimeStart = new Date(Long.parseLong(atStart));
        this.dateTimeEnd = new Date(Long.parseLong(atEnd));
        this.isDone = i.equals("1");
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
        return "E | " + boolToInt + " | " + this.description + " | " + this.dateTimeStart.getTime() + " | " //saved as long
                + this.dateTimeEnd.getTime() + "\n";
    }

    public void setDateTimeStart(Date newDateTimeStart) {
        this.dateTimeStart = newDateTimeStart;
    }

    public void setDateTimeEnd(Date newDateTimeEnd) {
        this.dateTimeEnd = newDateTimeEnd;
    }
}
