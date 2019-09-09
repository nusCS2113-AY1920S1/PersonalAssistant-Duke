package task;

import parser.TimeParser;

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
    public Event(String description, Date from, Date to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a summary of the event.
     *
     * @return Returns a summary of the event, including the description, the status icon, and the start and end time.
     */
    public String toString() {
        return String.format(super.toString(), "\uD83D\uDCC5") + String.format(" (%s -- %s)", TimeParser.convertDateToString(from), TimeParser.convertDateToString(to));
    }
}
