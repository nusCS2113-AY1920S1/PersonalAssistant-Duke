package models.tasks;

import exceptions.InvalidDateTimeException;
import models.commands.IDateSettable;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event implements ITask, IDateSettable, Serializable {
    /**
     * Class representing the Event class model.
     */
    private String description;
    private boolean isDone;
    private String initials;
    private String eventDateTimeString;
    private Date eventDateTimeObject;

    /**
     * Constructor of Event data model.
     *
     * @param description : Description of the Event
     * @param eventDateTimeString : Timing at which the Event is held
     */
    public Event(String description, String eventDateTimeString, Date eventDateTimeObject)
        throws InvalidDateTimeException {
        this.description = description;
        this.isDone = false;
        this.initials = "E";
        this.eventDateTimeString = eventDateTimeString;
        this.eventDateTimeObject = eventDateTimeObject;
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
        return this.description + " (at: " + this.eventDateTimeString + ")";
    }

    @Override
    public String getInitials() {
        return this.initials;
    }

    @Override
    public String getDateTime() {
        return this.eventDateTimeString;
    }

    @Override
    public Date getDateTimeObject() {
        return this.eventDateTimeObject;
    }

    public void setDateTime(String newDateTime) {
        this.eventDateTimeString = newDateTime;
    }
}
