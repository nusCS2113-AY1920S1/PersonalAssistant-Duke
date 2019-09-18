package duke.task;

import duke.parser.Parser;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Represents an Event, a {@link Task} which is happening at a specific Date and time
 */
public class Event extends Task {
    private String at;
    private Date date;

    public Event(String description, String at) {
        super(description);
        this.at = at;
        this.date = Parser.getDate(at);
    }

    @Override
    public void setNewDate(String date) {
        at=date;
        this.date=Parser.getDate(at);
    }

    @Override
    public Date getCurrentDate() {
        return date;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(at: " + Parser.getDateString(date, at) + ")";
    }
     /**
     * Returns the String representation of the {@link Event} in format compatible to be easily read and written in a text file on the hard disc
     * @return String used to print the {@link Task } in the text file
     */
    public String printInFile() {
        return this.isDone() ? "E|1|" + getDescription() + "|" + Parser.getDateString(date, at) : "E|0|" + this.getDescription() + "|" + Parser.getDateString(date, at);
    }

}