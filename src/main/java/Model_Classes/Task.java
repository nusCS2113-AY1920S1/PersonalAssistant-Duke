package Model_Classes;

import Enums.Priority;
import Enums.RecurrenceScheduleType;

import java.util.Date;

/**
 * Parent class for all other types of tasks
 */
public abstract class Task{
    private String description;
    private boolean isDone;
    private Date date;
    private Priority priority;
    private String assignee;
    private RecurrenceScheduleType recurrenceSchedule;
    private boolean hasRecurring;

    /**
     * Constructor for the task object. takes in the description of the task
     * @param description Description of the task
     */
    public Task(String description, Date date) {
        this.description = description;
        this.isDone = false;
        this.priority = Priority.low;
        this.date = date;
        this.assignee = "everyone";
        this.recurrenceSchedule = RecurrenceScheduleType.none;
    }

    /**
     * Returns the description of the task
     * @return description Description of the task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the task
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the time of the Task ( deadline of Assignment / time of meeting )
     * @return time task is due or starts
     */
    public Date getDate() { return date; }

    /**
     * Sets the date and time of the task
     * @param date date and time of the task
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * returns whether the task has been done
     * @return isDone The state of completion of the task.
     */
    public boolean getDone() {
        return isDone;
    }

    /**
     * Sets the task to be done
     */
    public void setDone(boolean done) {
        isDone = done;
    }

    /**
     * Returns String of the assignee that was specified
     * @return name of the user
     */
    public String getAssignee() {
        return this.assignee;
    }

    /**
     * Set the assignee of the task
     * @param assignee name of the assignee
     */
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    /**
     * Returns the priority of the task
     * @return priority of the task
     */
    public Priority getPriority() { return priority; }

    /**
     * Sets the priority of the task
     * @param p priority of the task
     */
    public void setPriority(Priority p) {
        priority = p;
    }

    /**
     * Gets the recurrence schedule of the task
     * @return the recurrence schedule of the task
     */
    public RecurrenceScheduleType getRecurrenceSchedule() {
        return recurrenceSchedule;
    }

    /**
     * Sets the recurrence schedule of the task
     * @param recurrenceSchedule the recurrence schedule that the task is set to
     */
    public void setRecurrenceSchedule(RecurrenceScheduleType recurrenceSchedule) {
        this.recurrenceSchedule = recurrenceSchedule;
        if (recurrenceSchedule.equals(RecurrenceScheduleType.none)) {
            this.hasRecurring = false;
        } else {
            this.hasRecurring = true;
        }
    }

    /**
     * Return whether the task is recurred
     * @return hasRecurring: whether the task is recurred
     */
    public boolean hasRecurring() {
        return hasRecurring;
    }

    /**
     * Snoozes the task by set amount of months
     * @param amount number of months to snooze
     */
    public void snoozeMonth(int amount) {
        this.date.setMonth(this.date.getMonth() + amount);;
    }

    /**
     * Snoozes the task by set amount of days
     * @param amount number of days to snooze
     */
    public void snoozeDay(int amount) {
        this.date.setDate(this.date.getDate() + amount);;
    }


    /**
     * Snoozes the task by set amount of hours
     * @param amount number of hours to snooze
     */
    public void snoozeHour(int amount){
        this.date.setHours(this.date.getHours() + amount);
    }


    /**
     * Snoozes the task by set amount of hours
     * @param amount number of minutes to snooze
     */
    public void snoozeMinute(int amount){
        this.date.setMinutes(this.date.getMinutes() + amount);
    }

    /**
     * Returns both the status icon and the description of the task.
     * @return the information of the task, consisting of status icon, description and assignee
     */
    public String toString() {
        if (hasRecurring)
            return " " + getDescription() + " " + "(" + getAssignee() + ") (every "
                    + getRecurrenceSchedule().toString() + ")";
        return " " + getDescription() + " " + "(" + getAssignee() + ")";
    }
}
