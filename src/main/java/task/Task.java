package task;

import javax.xml.stream.Location;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Period;

/**
 * This Task class is extended by the other tasks and serves as a template for
 * all tasks.
 *
 * @author Sai Ganesh Suresh
 * @version v2.0
 */
public abstract class Task implements Serializable {

    public String description;
    public Priority priority;
    public Reminder reminder;
    public String comment;

    protected boolean isIgnored;
    protected boolean isDone;
    public boolean isPrioritizable = true;
    public boolean hasLocation = false;

    public LocalDateTime endDate = null;
    public LocalDateTime startDate = null;
    public LocalDateTime createdDate;
    public Location locationOfTask;
    public Period eventPeriod;


    /**
     * Constructor for task.
     *
     * @param description The description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.isIgnored = false;
        this.priority = Priority.MEDIUM;
        this.createdDate = LocalDateTime.now();
        this.comment = "";
    }

    /**
     * Check if any task reminders are triggered.
     *
     * @return if triggered
     */
    public boolean checkReminderTrigger() {
        if (isIgnored) {
            return false;
        }
        if (reminder != null) {
            return reminder.checkReminderTrigger();
        }
        return false;
    }

    /**
     * Returns a priority symbol to be printed as output.
     *
     * @return Unicode that represent priority level.
     */
    public String getPriorityIcon() {
        if (!isPrioritizable) {
            return "[\u26A0]"; // Return warning sign symbol
        }
        if (priority == Priority.HIGH) {
            return "[\u2605\u2605\u2605]"; // Return triple star symbols
        } else if (priority == Priority.MEDIUM) {
            return "[\u2605\u2605]";// Return double star symbol
        } else {
            return "[\u2605]";// Return single star symbol
        }
    }

    /**
     * This getStatusIcon function returns the tick or cross symbols to be printed
     * as output.
     *
     * @return This function returns either a tick or a cross.
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); // Return tick or cross symbol
    }

    public String getDescription() {
        return description;
    }

    public void setReminder(int days) {
        reminder = new Reminder(days, startDate);
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void markAsIgnorable() {
        this.isIgnored = true;
        this.isPrioritizable = false;
    }

    public void markAsUnignorable() {
        this.isIgnored = false;
        this.isPrioritizable = true;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * converts the task to a string.
     */
    public String toString() {
        String message = "[" + getPriorityIcon() + "]" + "[" + getStatusIcon() + "] " + description;
        if (!comment.isBlank()) {
            message = message + "  Note to self: " + comment;
        }
        return message;
    }

    abstract boolean checkForClash(Task taskToCheck);
}