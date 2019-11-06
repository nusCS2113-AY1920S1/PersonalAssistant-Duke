package chronologer.task;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This Task class is extended by the other tasks and serves as a template for
 * all tasks.
 *
 * @author Sai Ganesh Suresh
 * @version v2.0
 */
public abstract class Task implements Serializable {

    protected String type;
    protected String description;
    protected LocalDateTime startDate = null;
    protected LocalDateTime endDate = null;
    protected String location = "";
    protected Priority priority;
    protected Reminder reminder;
    protected String comment;
    protected boolean isIgnored;
    protected boolean isDone;
    protected String modCode;

    private static final String TICK = "\u2713"; // Tick symbol
    private static final String CROSS = "\u2718"; // Cross symbol
    private static final String PRIORITY_LOW = "[\u2605]";// Low priority symbol
    private static final String PRIORITY_MED = "[\u2605\u2605]";// Med priority symbol
    private static final String PRIORITY_HIGH = "[\u2605\u2605\u2605]"; // High priority symbol

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
        this.comment = "";
        this.modCode = "";
        this.location = "";
    }

    /**
     * Check if any task reminders are triggered.
     *
     * @return if triggered
     */
    public boolean isReminderTrigger() {
        if (isIgnored) {
            return false;
        }
        if (reminder != null) {
            return reminder.isReminderTrigger();
        }
        return false;
    }

    /**
     * Returns a priority symbol to be printed as output.
     *
     * @return Unicode that represent priority level.
     */
    public String getPriorityIcon() {
        if (priority == Priority.HIGH) {
            return PRIORITY_HIGH; // Return triple star symbols
        } else if (priority == Priority.MEDIUM) {
            return PRIORITY_MED;// Return double star symbol
        } else {
            return PRIORITY_LOW;// Return single star symbol
        }
    }

    /**
     * This getStatusIcon function returns the tick or cross symbols to be printed
     * as output.
     *
     * @return This function returns either a tick or a cross.
     */
    public String getStatusIcon() {
        return (isDone ? TICK : CROSS); // Return tick or cross symbol
    }

    /**
     * converts the task to a string.
     */
    public String toString() {
        String message = getPriorityIcon() + "[" + getStatusIcon() + "] " + description;
        if (!comment.isBlank()) {
            message = message + "\nNote to self: " + comment;
        }
        if (!location.isBlank()) {
            message = message + "\n" + location;
        }
        return message;
    }

    public String getType() {
        return type;
    }

    abstract boolean isClash(Task taskToCheck);

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(int days) {
        reminder = new Reminder(days, startDate);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isIgnored() {
        return isIgnored;
    }

    public void setIgnored(boolean isIgnored) {
        this.isIgnored = isIgnored;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public String getModCode() {
        return modCode;
    }

}