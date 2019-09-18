package tasks;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Provide support for managing tasks that need to be done within a certain period
 * e.g., collect certificate between Jan 15 and 25th.
 */
public class Period extends Task{
    Date start;
    Date end;

    public Period(){super();}

    public Period(String description, Date start, Date end){
        super(description);
        this.start = start;
        this.end = end;
    }

    public void setStart(Date start){
        this.start = start;
    }

    public void setEnd(Date end){
        this.end = end;
    }

    @Override
    public String toString() {
        return "[P]" + super.toString() + " (from: " + start + " to: " + end + ")";
    }

    @Override
    public String dataString() {
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hhmm");
        return "P | " + (this.isDone ? 1 : 0) + " | " + this.description + " | " + ft.format(this.start)+" | " + ft.format(this.end);
    }
}
