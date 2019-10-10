package duke.task;

import java.util.Date;

/**
 * Represent an event task and inherits all the fields and methods of Task parent class.
 */
public class Event extends Task {

    protected String at;

    /**
     * Constructor for class Event.
     * @param description String containing the description of the task
     * @param at String containing the venue of the event
     */
    public Event(String description, String at) {
        super(description);
        this.at = at;
    }

    /**
     * Converts user input command to a standardized format to store in file.
     * @return String containing the standardized format
     */
    @Override
    public String toSaveString() {
        return "E" + super.toSaveString() + " | " + at;
    }

    /**
     * Converts user input command to a standardized format in taskList.
     * @return String containing the standardized format
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + at + ")";
    }

    public Date getDateTime() {
        return null;
    }
}