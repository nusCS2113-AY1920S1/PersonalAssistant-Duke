package duke.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import duke.parser.TimeParser;

import java.util.Date;

/**
 * Represents an Event. An Event is a task with begin time and end time.
 */
public class Event extends Task {

    protected Date from;
    protected Date to;

    /**
     * Constructor for Event.
     *
     * @param description the description of the Event.
     * @param from        start time of the Event.
     * @param to          end time of the Event.
     */
    public Event(@JsonProperty("description") String description, @JsonProperty("from") Date from, @JsonProperty("to") Date to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public Event(String description, Date from) {
        super(description);
        this.from = from;
        this.to = null;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    /**
     * Returns a summary of the event.
     * @return Returns a summary of the event, including the description, the status icon, and the start and end time.
     */
    public String toString() {
        if (this.to != null) {
            return String.format("  %s  \n  %s   ", TimeParser.convertDateToString(from), TimeParser.convertDateToString(to));
        } else {
            return String.format("  %s  ", TimeParser.convertDateToString(from));
        }
    }
}
