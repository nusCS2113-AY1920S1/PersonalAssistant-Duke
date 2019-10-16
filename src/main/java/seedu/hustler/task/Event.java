package seedu.hustler.task;

import java.time.LocalDateTime;

import static seedu.hustler.parser.DateTimeParser.convertDateTime;
import static seedu.hustler.parser.DateTimeParser.toDateTimeString;

/**
 * A class that inherits from the abstract class Task. This task type
 * is an Event which specifies a Task at a specific date and time.
 */
public class Event extends Task {
    /**
     * LocalDateTime to store the event's date and time.
     */
    protected LocalDateTime at;

    /**
     * Initializes description, default difficulty and at.
     */
    public Event(String description, LocalDateTime at) {
        super(description);
        this.at = at;
    }

    /**
     * Initializes description, user input difficulty and by.
     */
    public Event(String description,  LocalDateTime at, String difficulty, String tag, LocalDateTime now) {
        super(description, difficulty, tag, now);
        this.at = at;
    }

    /**
     * Overrides the toString method in Task to display task type and date time.
     *
     * @return a string with the target info.
     */
    public String toString() {
        return "[E]" + super.toString() + " (at: " + toDateTimeString(this.at) + ")";
    }

    /**
     * Overrides the toSaveFormat function to include task type and date time.
     *
     * @return a string with pipe separated info.
     */
    public String toSaveFormat() {
        return "E|" + super.toSaveFormat() + "|" + convertDateTime(at) + super.toSaveInputDateTime();
    }

    /**
     * Checks equality with another Event instance.
     *
     * @param temp the instance to compare against.
     * @return true or false to the comparison.
     */
    public boolean equals(Event temp) {
        if (this.description.equals(temp.description) && this.at == temp.at) {
            return true;
        }
        return false;
    }

    @Override
    public LocalDateTime getDateTime() {
        return this.at;
    }

    @Override
    public void setDateTime(LocalDateTime localDateTime) {
        this.at = localDateTime;
    }
}