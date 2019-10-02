package models.tasks;

import java.util.Arrays;
import java.util.Date;

public class Tentative implements ITask {
    private String description;
    private boolean isDone;
    private String initials;
    private String[] tentativeDateTimeStrings;
    private Date[] tentativeDateTimeObjects;

    /**
     * Constructor for Tentative event data model
     * @param description description or name of tentative event
     * @param tentativeDateTimeStrings array of formatted tentative dates in String format
     * @param tentativeDateTimeObjects array of formatted tentative dates in Date format
     */
    public Tentative(String description, String[] tentativeDateTimeStrings, Date[] tentativeDateTimeObjects) {
        //correct format:  tentative <name of task> /at <tentative date and time> or <tentative date and time> ...
        this.description = description;
        this.isDone = false;
        this.initials = "TE";
        this.tentativeDateTimeStrings = tentativeDateTimeStrings;
        this.tentativeDateTimeObjects = tentativeDateTimeObjects;
    }

    @Override
    public String getStatusIcon() {
        return (isDone ? "✓" : "✗"); //return tick or X symbols
    }

    @Override
    public void markAsDone() {
        this.isDone = true;
    }

    @Override
    public String getDescription() {
        String dates = String.join(" or ", this.tentativeDateTimeStrings);
        return this.description + " (at: " + dates + ")";
    }

    public String getDescriptionOnly() {
        return this.description;
    }

    @Override
    public String getInitials() {
        return this.initials;
    }

    @Override
    public String getDateTime() {
        return null;
    }

    @Override
    public Date getDateTimeObject() {
        return null;
    }

    public String[] getTentativeDateTimeStrings() {
        return this.tentativeDateTimeStrings;
    }

    public Date[] getTentativeDateTimeObjects() {
        return this.tentativeDateTimeObjects;
    }

    @Override
    public void setDateTime(String newDateTime) {

    }
}
