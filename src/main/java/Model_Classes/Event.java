package Model_Classes;

import CustomExceptions.DukeException;

import java.util.Date;

public class Event extends Task {
    private Date by;
    public Event(String description, Date by)  {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (on: " + by + ")";
    }

    public Date getBy() {
        return by;
    }
}
