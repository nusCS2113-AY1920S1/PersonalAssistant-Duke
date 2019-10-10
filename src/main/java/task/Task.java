package task;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Period;

import static parser.DateTimeExtractor.NULL_DATE;

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
    public int remindInHowManyDays = 0;

    protected boolean isIgnored;
    protected boolean isDone;
    public boolean isPrioritizable = true;

    public LocalDateTime endDate = NULL_DATE;
    public LocalDateTime startDate = NULL_DATE;
    public LocalDateTime createdDate;
    public Period eventPeriod;

    /**
     * Constructor for task.
     * @param description The description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.isIgnored = false;
        this.priority = Priority.MEDIUM;
        this.createdDate = LocalDateTime.now();
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
        if (!startDate.isEqual(NULL_DATE)) {
            LocalDateTime reminderDate = startDate.minusDays(remindInHowManyDays);
            return LocalDateTime.now().isAfter(reminderDate);
        }
        return false;
    }

    /**
     * Returns a priority symbol to be printed
     * as output.
     *
     * @return Unicode that represent priority level.
     */
    public String getPriorityIcon() {
        if (!isPrioritizable) {
            return "[\u26A0]"; //Return warning sign symbol
        }
        if (priority == Priority.HIGH) {
            return "[\u2605\u2605\u2605]"; //Return triple star symbols
        } else if (priority == Priority.MEDIUM) {
            return "[\u2605\u2605]";//Return double star symbol
        } else {
            return "[\u2605]";//Return single star symbol
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
        this.remindInHowManyDays = days;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void markAsIgnorable() {
        this.isIgnored = true;
        this.isPrioritizable = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public String toString() {
        return "[" + getPriorityIcon() + "]" + "[" + getStatusIcon() + "] " + description;
    }

    abstract boolean checkForClash(Task taskToCheck);
}