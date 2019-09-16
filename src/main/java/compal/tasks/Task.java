package compal.tasks;

import java.io.Serializable;
import java.util.Date;

public abstract class Task implements Serializable {

    public boolean isDone;
    protected String symbol;
    private int id;
    private Date dateTime;
    private String taskTypeString;
    private String description;
    private boolean hasReminder;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718");
    }

    public void markAsDone() {
        isDone = true;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isHasReminder() {
        return hasReminder;
    }

    public void setHasReminder() {
        this.hasReminder = true;
    }
}
