package seedu.hustler.task;

import seedu.hustler.ui.Ui;
import java.time.LocalDateTime;

import static seedu.hustler.parser.DateTimeParser.convertDateTime;
import static seedu.hustler.parser.DateTimeParser.toDateTimeString;

/**
 * This task type inherits from Task. It specifies an event at a particular time.
 */
public class Event extends Task {
    /**
     * LocalDateTime to store the event's date and time.
     */
    protected LocalDateTime at;

    /**
     * Object of the Ui class that that is used to
     * communicate errors to the user.
     */
    private Ui ui = new Ui();

    /**
     * Initializes description and at variable.
     */
    public Event(String description, LocalDateTime at) {
        super(description);
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
        return "E|" + super.toSaveFormat() + "|" + convertDateTime(at);
    }

    /**
     * Checks equality with another Event instance.
     *
     * @param temp the instance to compare against.
     * @return true or false to the comparison.
     */
    public boolean equals(Event temp) {
        if (this.description == temp.description && this.at == temp.at) {
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
