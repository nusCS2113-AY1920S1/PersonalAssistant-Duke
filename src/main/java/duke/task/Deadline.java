package duke.task;

import duke.parser.Convert;

import java.util.Date;

/**
 * Represents a deadline {@link Task } specified by the due {@link Date}.
 */
public class Deadline extends Task {

    private String by;
    private Date date;

    /**
     * The constructor method for Deadline.
     */
    public Deadline(String description, String str) {
        super(description);
        this.setNewDate(str);
    }

    @Override
    public void setNewDate(String date) {
        this.by = date;
        this.date = Convert.stringToDate(by);
    }

    @Override
    public Date getCurrentDate() {
        return date;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by: " + by + ")";
    }

    /**
     * Formats {@link Deadline} into a String.
     *
     * @return String used to print the {@link Task } in the text file
     */
    @Override
    public String printInFile() {
        if (this.isDone()) {
            return "D|1|" + this.getDescription() + "|" + by;
        } else {
            return "D|0|" + this.getDescription() + "|" + by;
        }
    }
}