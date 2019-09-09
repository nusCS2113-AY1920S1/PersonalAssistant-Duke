package duke.tasks;

import java.time.LocalDateTime;

/**
 * Class representing an event.
 */
public class Event extends Task {
    private LocalDateTime startDate;
    private String event;

    /**
     * Initializes a event not yet done with the given description and a date.
     *
     * @param description A description of this event.
     */
    public Event(String description, LocalDateTime startDate) {
        super(description);
        this.startDate = startDate;
    }

    /**
     * Initializes a event not yet done with the given description.
     *
     * @param description A description of this event.
     */
    public Event(String description, String event) {
        super(description);
        this.event = event;
    }

    /**
     * Returns a string representation of this event.
     *
     * @return The desired string representation.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + (startDate == null ? event : startDate) + ")";
    }

    public String getEvent() {
        return startDate == null ? event : startDate.toString();
    }
}
