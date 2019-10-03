package duke.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event that allows for a timing to be set.
 */
public class Event extends Task {

    LocalDateTime at;

    /**
     * Takes in the event descriptio and the start time of the event.
     * @param description A String representing the event taking place
     * @param at LocalDateTime object representing the start time of the event
     */
    public Event(String description, LocalDateTime at) {
        super(description);
        this.at = at;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + dateToString(at) + ")";
    }

    /**
     * Converts the input LocalDateTime to printable format in String.
     * @param dateTime LocalDateTime object to be converted to String
     * @return String format of the LocalDateTime
     */
    private String dateToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        return dateTime.format(formatter);
    }

    public void setAt(LocalDateTime tillValue) {
        at = tillValue;
    }
}