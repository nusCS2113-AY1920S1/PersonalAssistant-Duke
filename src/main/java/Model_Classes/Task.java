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

    /**
     * Constructor for the task object. takes in the description of the task
     * @param description Description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.priority = Priority.low;
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
}
