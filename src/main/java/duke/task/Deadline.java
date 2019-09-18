package duke.task;

import duke.parser.Parser;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Represents a deadline {@link Task } specified by the due {@link Date}
 */
public class Deadline extends Task {
    private String by;
    private Date date;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        this.date = Parser.getDate(by);
    }

    @Override
    public void setNewDate(String date) {
        by=date;
        this.date=Parser.getDate(by);
    }

    @Override
    public Date getCurrentDate() {
        return date;
    }


    @Override
    public String toString() {
        return (Parser.getDate(by) == null) ? "[D]" + super.toString() + "(by: " + by + ")" : "[D]" + super.toString() + "(by: " + Parser.getDateString(date,by) + ")";
    }

    /**
     * Returns the data by which the deadline must be met
     * @return date the final {@link Date } for finishing the Deadline
     */
    public Date getDate() {
        return date;
    }


    /**
     * Returns the String representation of the {@link Deadline} in format compatible to be easily read and written in a text file on the hard disc
     * @return String used to print the {@link Task } in the text file
     */
    public String printInFile() {
        return this.isDone() ? "D|1|" + this.getDescription() + "|" + Parser.getDateString(date, by) : "D|0|" + this.getDescription() + "|" + Parser.getDateString(date, by);
    }
}