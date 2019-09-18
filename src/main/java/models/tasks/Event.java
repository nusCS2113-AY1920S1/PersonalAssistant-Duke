package models.tasks;

import models.commands.IDateSettable;

import java.io.Serializable;

public class Event implements ITask, IDateSettable, Serializable {
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
     * @param eventDate : Timing at which the Event is held
     */
    public Event(String description, String eventDate) {
        this.description = description;
        this.isDone = false;
        this.initials = "E";
        this.timing = eventDate;
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

    @Override
    public String getDateTime() {
        return this.timing;
    }

    public void setDateTime(String newDateTime) {
        this.timing = newDateTime;
    }
}
