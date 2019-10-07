package models.temp.tasks;

import java.util.Date;

public class Recurring implements ITask, IRecurring {
    private boolean isDone;
    private String initials;
    private String description;
    private String dueDate;
    private int daysLeft;
    private Date startDate;

    /**
     * Constructor of the Recurring data model.
     *
     * @param description : Description of the new Recurring task
     * @param dueDate : Due date of this recurring task.
     *                Will also be the last day before recurring task recreates itself
     */
    public Recurring(String description, String dueDate, Date date) {
        this.isDone = false;
        this.initials = "R";
        this.description = description;
        this.dueDate = dueDate;
        this.daysLeft = 7;
        this.startDate = date;
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
    public String getFullDescription() {
        return null;
    }

    @Override
    public Date getDateTimeObject() {
        return null;
    }

    @Override
    public int getDaysLeft() {
        return this.daysLeft;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    @Override
    public String getRecurringDescription() {
        return this.description;
    }

    @Override
    public void setDateTime(String newDateTime) {
        this.dueDate = newDateTime;
    }
}
