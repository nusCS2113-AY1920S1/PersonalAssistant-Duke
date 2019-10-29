package duke.task;

import duke.exception.DukeException;
import duke.extensions.Priority;
import duke.extensions.Recurrence;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Superclass for all Tasks that will be added to the duke.task.Task Manager
 */
public class Task {
    protected Optional<LocalDateTime> dateTime;
    protected boolean isDone;
    protected String key;
    protected Priority priority;
    protected Recurrence recurrence;
    private String description;
    private int duration;
    private Optional<String> filter;

    /**
     * Constructor function for duke.task.Task
     * Creates a new instance of duke.task.Task by taking in a String description
     * Automatically flags the boolean isDone as False
     * Default Tasks have no type
     *
     * @param description the description of the task
     */
    public Task(Optional<String> filter, Optional<LocalDateTime> dateTime, Recurrence recurrence,
                String description, int duration) {
        this.description = description;
        this.isDone = false;
        this.key = "";
        this.priority = Priority.LOW;
        this.recurrence = recurrence;
        this.filter = filter;
        this.duration = duration;
        this.dateTime = dateTime;
    }

    public Task(Task other) {
        this.description = other.description;
        this.isDone = other.isDone;
        this.key = other.key;
        this.priority = other.priority;
        this.recurrence = other.recurrence;
        this.filter = other.filter;
        this.duration = other.duration;
        this.dateTime = other.dateTime;
    }

    /**
     * Returns a String object to show if a duke.task.Task has been marked done or not
     *
     * @return tick if done or X symbol if not done
     */
    public String getStatusIcon() {
        return (isDone ? "Y" : "N");
    }

    /**
     * Flags the boolean attribute isDone as true in a task
     * Prints out the confirmation that the task is marked done
     */
    public void markAsDone() {
        isDone = true;
    }

    public void markAsUndone() {
        isDone = false;
    }


    public Optional<String> getFilter() {
        return this.filter;
    }

    public void setFilter(Optional<String> filter) {
        this.filter = filter;
    }

    public String getRecurrenceCode() {
        return recurrence.recurrenceCode();
    }

    public void setRecurrence(String recurrence) throws DukeException {
        this.recurrence = new Recurrence(Optional.of(recurrence));
        System.out.println(this + " has a new recurrence period of " + recurrence);
    }

    public void updateDone() {
        if (recurrence.isTimeToReset()) {
            markAsUndone();
        }
    }

    public String getDuration() {
        if (duration == 0) {
            return "N";
        }
        return Integer.toString(duration);
    }

    public void setDuration(int duration) {
        this.duration = duration;
        System.out.println(this + " has a new duration of " + duration + ((duration == 1) ? "hour" : " hours"));
    }

    public int getPriorityLevel() {
        return priority.priorityLevel();
    }

    public String getPriority() {
        return priority.priorityCode();
    }

    public void setPriority(int i) throws DukeException {
        switch (i) {
        case 0:
            priority = Priority.LOW;
            System.out.println(this + " has a new priority of LOW");
            break;
        case 1:
            priority = Priority.MEDIUM;
            System.out.println(this + " has a new priority of MEDIUM");
            break;
        case 2:
            priority = Priority.HIGH;
            System.out.println(this + " has a new priority of HIGH");
            break;
        default:
            throw new DukeException("No such priority exists.");
        }
    }

    /**
     * Returns a String which describes the task
     *
     * @return the description of the task
     */
    public String getDescription() {
        return dateTime.map(localDateTime ->
                this.description + " "
                        + localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
                .orElseGet(() -> this.description);
    }

    public void setDescription(String description) {
        this.description = description;
        System.out.println(this + " description has been successfully updated");
    }

    public boolean hasDateTime() {
        return dateTime.isPresent();
    }

    public LocalDateTime getDateTime() {
        return dateTime.get();
    }

    public void setDateTime(Optional<LocalDateTime> dateTime) {
        this.dateTime = dateTime;
        System.out.println(this + " has a new deadline of "
                + dateTime.get().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }

    @Override
    public String toString() {
        String recurringIcon = recurrence.recurrenceIcon();
        return recurringIcon + key + "[" + this.getStatusIcon() + "] " + description
                + (dateTime.isPresent()
                ? " (" + dateTime.get().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + ")"
                : "");
    }
}
