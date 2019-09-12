package Model_Classes;

import CustomExceptions.DukeException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Deadline extends Task {
    private Date by;

    public Deadline (String description, Date by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    public Date getBy() {
        return by;
    }
}
