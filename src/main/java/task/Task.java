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

    public String description; // basically similar to describing features of the class
    protected boolean isDone;
    public LocalDateTime endDate = NULL_DATE;
    public LocalDateTime startDate = NULL_DATE;
    public LocalDateTime createdDate;
    public Period eventPeriod;
    public int remindInHowManyDays = 0;

    /**
     * This task constructor is used to obtain the parameters required by the task
     * class.
     *
     * @param description This string holds the description provided by the user.
     */
    public Task(String description) { // constructor
        this.description = description;
        this.isDone = false;
        this.createdDate = LocalDateTime.now();
    }

    /**
     * This getStatusIcon function returns the tick or cross symbols to be printed
     * as output.
     *
     * @return This function returns either a tick or a cross.
     */
    public String getStatusIcon() { // return tick or X symbols
        return (isDone ? "\u2713" : "\u2718");

        // The GUI requires the above version which essentially returns as below!
        // return (isDone ? "✓" : "✘");
    }

    /**
     * This markAsDone function allows the user to mark a task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * This toString function of the task class etches the different portions of the
     * user input into a single string.
     *
     * @return This function returns a string of the required task in the desired
     * output format of string type.
     */
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    public void setReminder(int days) {
        this.remindInHowManyDays = days;
    }

    /**
     * check if any task reminders are triggered.
     *
     * @return if triggered
     */
    public boolean checkReminderTrigger() {
        if (!startDate.isEqual(NULL_DATE)) {
            LocalDateTime reminderDate = startDate.minusDays(remindInHowManyDays);
            return LocalDateTime.now().isAfter(reminderDate);
        }
        return false;
    }

    abstract boolean checkForClash(Task taskToCheck);
}