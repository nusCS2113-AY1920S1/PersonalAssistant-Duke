package models.tasks;

import exceptions.InvalidDateTimeException;
import java.io.Serializable;
import java.net.PasswordAuthentication;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event implements ITask, Serializable {
    /**
     * Class representing the Event class model.
     */
    private String description;
    private boolean isDone;
    private String initials;
    private String eventDate;
    private Date eventDateTime;

    /**
     * Constructor of Event data model.
     *
     * @param description : Description of the Event
     * @param eventDate : Timing at which the Event is held
     */
    public Event(String description, String eventDate) throws InvalidDateTimeException {
        this.description = description;
        this.isDone = false;
        this.initials = "E";
        this.eventDate = eventDate;
        SimpleDateFormat format = new SimpleDateFormat("dd MMMMM yyyy HH.mm a");
        try {
            this.eventDateTime = format.parse(eventDate);
        } catch (ParseException e) {
            throw new InvalidDateTimeException();
        }
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
        return this.description + " (at: " + this.eventDate + ")";
    }

    @Override
    public String getInitials() {
        return this.initials;
    }

    @Override
    public String getDateTime() {
        return this.eventDate;
    }

    @Override
    public Date getDateTimeObject() {
        return this.eventDateTime;
    }

}
