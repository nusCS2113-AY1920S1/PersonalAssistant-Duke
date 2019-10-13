package duke.task;

import duke.parser.Convert;

import java.util.Date;

/**
 * Represents an Event, a {@link Task} which is happening at a specific Date and time.
 */
public class Event extends Task {

    private String at;
    private Date date;

    /**
     * The constructor method for Event.
     */
    public Event(String description, String str) {
        super(description);
        this.setNewDate(str);
    }

    @Override
    public void setNewDate(String date) {
        this.at = date;
        this.date = Convert.stringToDate(at);
    }

    @Override
    public Date getCurrentDate() {
        return date;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(at: " + at + ")";
    }

    /**
     * Formats {@link Event} into a String.
     *
     * @return String used to print the {@link Task } in the text file
     */
    @Override
    public String printInFile() {
        if (this.isDone()) {
            return "E|1|" + this.getDescription() + "|" + at;
        } else {
            return "E|0|" + this.getDescription() + "|" + at;
        }
    }
}