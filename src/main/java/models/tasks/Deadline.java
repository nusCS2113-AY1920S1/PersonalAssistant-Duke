package models.tasks;

import models.commands.IDateSettable;

import java.io.Serializable;
import java.util.Date;

public class Deadline implements ITask, IDateSettable, Serializable {
    /**
     * Class representing the Deadline data model.
     */
    private String description;
    private boolean isDone;
    private String initials;
    private String dueDateTimeString;
    private Date dueDateTimeObject;


    /**
     * Constructor of Deadline data model.
     * @param description Description or name of deadline/
     * @param dateTimeString Formatted due date of deadline, in String format
     * @param dateTimeObject Due date of deadline, as a Date object
     */
    public Deadline(String description, String dateTimeString, Date dateTimeObject) {
        this.description = description;
        this.isDone = false;
        this.initials = "D";
        this.dueDateTimeString = dateTimeString;
        this.dueDateTimeObject = dateTimeObject;
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
        return this.description + " (by: " + this.dueDateTimeString + ")";
    }

    @Override
    public String getFullDescription() {
        return "[" + getInitials() + "][" + getStatusIcon() + "] " + getDescription();
    }

    @Override
    public String getInitials() {
        return this.initials;
    }

    @Override
    public String getDateTime() {
        return this.dueDateTimeString;
    }

    @Override
    public Date getDateTimeObject() {
        return this.dueDateTimeObject;
    }

    public void setDateTime(String newDueDate) {
        this.dueDateTimeString = newDueDate;
    }
}
