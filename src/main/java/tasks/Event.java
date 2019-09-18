package tasks;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Event extends Task {
    private Date at;

    /**
     * This is a class for abstraction of a Event type task.
     * @param description the description, or content of the event
     * @param at the time when the event happen
     */
    public Event(String description, Date at) {
        super(description);
        this.at = at;
    }

    public Event(String description) {
        super(description);
    }

    @Override
    public void setTime(Date at) {
        this.at = at;
    }

    public Date getTime()   {
        return at;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + at + ")";
    }

    @Override
    public String dataString() {
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hhmm");
        return "E | " + (this.isDone ? 1 : 0) + " | " + this.description + " | " + ft.format(this.at);
    }
}
