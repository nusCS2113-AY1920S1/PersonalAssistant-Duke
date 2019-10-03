package owlmoney.model.task;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * A generic task, which can be marked as done and has basic functions.
 */
public class Task {
    private final String description;
    private boolean done;
    private final LocalDate date;

    /**
     * Initializes a task not yet done with the given description.
     *
     * @param description A description of this task.
     */
    Task(String description) {
        this.description = description;
        this.done = false;
        this.date = LocalDate.now();
    }

    /**
     * Marks the task as done.
     */
    void markDone() {
        done = true;
    }

    /**
     * Marks the task as not done.
     */
    void markUnDone() {
        done = false;
    }

    /**
     * Gets a boolean equivalent of whether this task is done.
     *
     * @return true if this task is done and false when it is not done.
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Gets a character representing whether this task is done.
     *
     * @return tick if this task is done and cross when it is not done.
     */
    private String getDoneChar() {
        return done ? "✓" : "✘";
    }

    /**
     * Returns the description associated with this task.
     *
     * @return This task's description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns a string representation of this task.
     *
     * @return The desired string representation based on specifications.
     */
    @Override
    public String toString() {
        String boxedChar = "[" + getDoneChar() + "]";
        return boxedChar + " " + description;
    }

    /**
     * Exports this task in string format.
     * It is human readable and easily portable for other applications.
     *
     * @return A string representation of this task containing its done status (0 or 1)
     *         and its description.
     */
    public String export() {
        return (done ? "1 | " : "0 | ");
    }

    /**
     * Reschedules the date of the task.
     *
     * @param rescheduleTime Date to be rescheduled to.
     */
    void setDate(LocalDateTime rescheduleTime){
    }

    /**
     * Gets date of Deadline & Event.
     *
     * @return date only of DeadLine and Event
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns a LocalDateTime of this Task.
     *
     * @return The current date and time.
     */
    public LocalDateTime getDateTime() {
        return LocalDateTime.now();

    }
}
