package Model_Classes;

import Enums.Priority;
import Enums.RecurrenceScheduleType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Parent class for all other types of tasks
 */
public class Task{
    private String description;
    private boolean isDone;
    private Priority priority;
    private String user;
    private RecurrenceScheduleType recurrenceSchedule;
    private boolean hasRecurring;
    private ArrayList<String> subTasks;
    private Date time;

    /**
     * Constructor for the task object. takes in the description of the task
     * @param description Description of the task
     */
    public Task(String description, Date time) {
        this.description = description;
        this.isDone = false;
        this.priority = Priority.low;
        this.time = time;
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

    public void setUser(String user) {
        this.user = user;
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

    public RecurrenceScheduleType getRecurrenceSchedule() {
        return recurrenceSchedule;
    }

    public void setRecurrenceSchedule(RecurrenceScheduleType recurrenceSchedule) {
        this.recurrenceSchedule = recurrenceSchedule;
        if (recurrenceSchedule.equals(RecurrenceScheduleType.none)) {
            this.hasRecurring = false;
        } else {
            this.hasRecurring = true;
        }
    }

    public boolean hasRecurring() {
        return hasRecurring;
    }

    /**
     * Takes in a String, splits it by "," and sets each new String as a subtask of current Task
     * @param input String containing subtasks separated by ","
     */
    public void setSubTasks(String input) {
        subTasks = (ArrayList<String>) Arrays.asList(input.split(","));
    }

    /**
     * Returns the time of the Task ( deadline of Assignment / time of meeting )
     * @return time task is due or starts
     */
    public Date getDate() { return time; }

    /**
     * Snoozes the Event by set amount of months
     * @param amount number of months to snooze
     */
    public void snoozeMonth(int amount) {
        this.time.setMonth(this.time.getMonth() + amount);;
    }

    /**
     * Snoozes the Event by set amount of days
     * @param amount number of days to snooze
     */
    public void snoozeDay(int amount) {
        this.time.setDate(this.time.getDate() + amount);;
    }


    /**
     * Snoozes the Event by set amount of hours
     * @param amount number of hours to snooze
     */
    public void snoozeHour(int amount){
        this.time.setHours(this.time.getHours() + amount);
    }


    /**
     * Snoozes the Event by set amount of hours
     * @param amount number of minutes to snooze
     */
    public void snoozeMinute(int amount){
        this.time.setMinutes(this.time.getMinutes() + amount);
    }

}
