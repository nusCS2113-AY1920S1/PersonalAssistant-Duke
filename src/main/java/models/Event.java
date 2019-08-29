package models;

import java.io.Serializable;

public class Event implements ITask, Serializable {
    /**
     * Class representing the Event class model.
     */
    private String description;
    private boolean isDone;
    private String initials;
    private String timing;

    /**
     * Constructor of Event data model.
     *
     * @param description : Description of the Event
     * @param timing : Timing at which the Event is held
     */
    public Event(String description, String timing) {
        this.description = description;
        this.isDone = false;
        this.initials = "E";
        this.timing = timing;
    }

    @Override
    public String getStatusIcon() {
        return (isDone ? "✓" : "✗");
    }

    @Override
    public void markAsDone() {
        this.isDone = true;
    }

    @Override
    public String getDescription() {
        return this.description + " (at: " + this.timing + ")";
    }

    @Override
    public String getInitials() {
        return this.initials;
    }
}
