package Model_Classes;

import Enums.Priority;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Parent class for all other types of tasks
 */
public class Task{
    private String description;
    private boolean isDone;
    private String created;
    private Priority priority;
    private String user;

    /**
     * Constructor for the task object. takes in the description of the task
     * @param description Description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        DateTimeFormatter dateTimeFormatterNow = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        this.created = now.format(dateTimeFormatterNow);
        this.priority = Priority.low;
    }

    /**
     * Overload constructor for the task object. Takes in the user assigned to the task
     * @param description Description of the task
     * @param user User whom the tasks is assigned to
     */
    public Task(String description, String user) {
        this.description = description;
        this.isDone = false;
        DateTimeFormatter dateTimeFormatterNow = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        this.created = now.format(dateTimeFormatterNow);
        this.priority = Priority.low;
        this.user = user;
    }

    /**
     * Overload constructor for the task object. takes in the description of the task
     * @param description Description of the task.
     * @param done Whether the task is completed.
     * @param priority Priority of the task.
     */
    public Task(String description, boolean done, Priority priority) {
        this.description = description;
        this.isDone = done;
        DateTimeFormatter dateTimeFormatterNow = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        this.created = now.format(dateTimeFormatterNow);
        this.priority = priority;
    }

    /**
     * Returns String of the user that was specified
     * @return name of the user
     */
    public String getUser() {
        return this.user;
    }
    /**
     * Returns String of the time Task was created
     * @return time the task was created
     */
    public String getCreated() {
        return this.created;
    }

    /**
     * returns whether the task has been done
     * @return isDone The state of completion of the task.
     */
    public boolean getDone() {
        return isDone;
    }

    /**
     * Returns the status of the completion of the task.
     * shows a tick if done, and a cross if not done.
     * @return A String showing a tick or X symbol.
     */
    public String getStatusIcon() { return (isDone ? "[\u2713] " : "[\u2718] "); } //return tick or X symbols

    /**
     * Returns the description of the task
     * @return description Description of the task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the priority of the task
     * @return priority of the task
     */
    public Priority getPriority() { return priority; }

    /**
     * Sets the task to be done
     */
    public void setDone(boolean done) {
        isDone = done;
    }

    /**
     * Sets the priority of the task
     * @param p priority of the task
     */
    public void setPriority(Priority p) {
        priority = p;
    }

    /**
     * Returns both the status icon and the description of the task.
     * @return
     */
    public String toString() {
        return getStatusIcon() + getDescription() + " " + "(" + getUser() + ")";
    }

    /**
     * Snooze the task in year
     * @param amount number of years to snooze
     */
    public void snoozeYear(int amount) {
        return;
    }

    /**
     * Snooze the task in month
     * @param amount number of months to snooze
     */
    public void snoozeMonth(int amount) {
        return;
    }

    /**
     * Snooze the task in day
     * @param amount number of days to snooze
     */
    public void snoozeDay(int amount) {
        return;
    }

    /**
     * Snooze the task in hour
     * @param amount number of hours to snooze
     */
    public void snoozeHour(int amount) {
        return;
    }

    /**
     * Snooze the task in minute
     * @param amount number of minutes to snooze
     */
    public void snoozeMinute(int amount) {
        return;
    }

}
