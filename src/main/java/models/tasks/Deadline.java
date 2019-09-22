package models.tasks;

import exceptions.InvalidDateTimeException;
import models.commands.IDateSettable;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Deadline implements ITask, IDateSettable, Serializable {
    /**
     * Class representing the Deadline data model.
     */
    private String description;
    private boolean isDone;
    private String initials;
    private String dueDate;
    private Date dueDateTime;

    /**\
     * Constructor of Deadline data model.
     *
     * @param description : Description of new task
     * @param dueDate : Due date of deadline
     */
    public Deadline(String description, String dueDate) throws InvalidDateTimeException {
        this.description = description;
        this.isDone = false;
        this.initials = "D";
        this.dueDate = dueDate;
        SimpleDateFormat format = new SimpleDateFormat("dd MMMMM yyyy HH.mm a");
        try {
            this.dueDateTime = format.parse(dueDate);
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
        return this.description + " (by: " + this.dueDate + ")";
    }

    @Override
    public String getInitials() {
        return this.initials;
    }

    @Override
    public String getDateTime() {
        return this.dueDate;
    }

    @Override
    public Date getDateTimeObject() {
        return this.dueDateTime;
    }

    public void setDateTime(String newDueDate) {
        this.dueDate = newDueDate;
    }
}
