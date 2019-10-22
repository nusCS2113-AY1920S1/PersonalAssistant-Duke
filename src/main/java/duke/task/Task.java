package duke.task;
import duke.exception.DukeException;
import duke.extensions.Priority;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;


/**
 * Superclass for all Tasks that will be added to the duke.task.Task Manager
 */
public class Task {
    enum RecurrencePeriod {NONE, DAILY, WEEKLY}

    protected String description;
    protected boolean isDone;
    protected String key;
    protected LocalDate createdDate;
    protected RecurrencePeriod recurrencePeriod;
    protected Priority priority;
    protected Optional<String> filter;
    protected int duration;
    protected Optional<LocalDateTime> dateTime;

    /**
     * Constructor function for duke.task.Task
     * Creates a new instance of duke.task.Task by taking in a String description
     * Automatically flags the boolean isDone as False
     * Default Tasks have no type
     *
     * @param description the description of the task
     */
    public Task(String description, Optional<String> filter, String recurrencePeriod,
                int duration, Optional<LocalDateTime> dateTime) {
        this.description = description;
        this.isDone = false;
        this.key = "";
        this.createdDate = LocalDate.now();
        this.priority = Priority.LOW;
        switch (recurrencePeriod) {
            case "none":
                this.recurrencePeriod = RecurrencePeriod.NONE;
                break;
            case "daily":
                this.recurrencePeriod = RecurrencePeriod.DAILY;
                break;
            case "weekly":
                this.recurrencePeriod = RecurrencePeriod.WEEKLY;
                break;
        }
        this.filter = filter;
        this.duration = duration;
        this.dateTime = dateTime;
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

    public String getRecurring() {
        switch (recurrencePeriod) {
            case DAILY:
                return "D";
            case WEEKLY:
                return "W";
            default:
                return "N";
        }
    }

    public Optional<String> getFilter() {
        return this.filter;
    }

    /**
     * This function marks tasks as undone every week/day based on the
     * recurrence period of the task.
     */
    public boolean isTimeToReset(LocalDate dateCreated, LocalDate dateNow) {
        switch (recurrencePeriod) {
            case DAILY:
                if (ChronoUnit.DAYS.between(dateCreated, dateNow) > 0) {
                    return true;
                } else {
                    return false;
                }
            case WEEKLY:
                if (ChronoUnit.DAYS.between(dateCreated, dateNow) > 7) {
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }

    public void setDescription(String description) {
        this.description = description;
        System.out.println(this + " description has been successfully updated");
    }

    public void setPriority(int i) throws DukeException {
        switch(i) {
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

    public String getDuration() {
        if (duration == 0) {
            return "N";
        }
        return Integer.toString(duration);
    }

    public int getPriorityLevel() {
        return priority.priorityLevel();
    }

    public String getPriority() {
        return priority.priorityCode();
    }

    /**
     * Returns a String which describes the task
     *
     * @return the description of the task
     */
    public String getDescription() {
        return dateTime.map(localDateTime ->
            this.description + " " +
            localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
            .orElseGet(() -> this.description);
    }

    public boolean hasDateTime() {
        return dateTime.isPresent();
    }

    public LocalDateTime getDateTime() {
        return dateTime.get();
    }

    @Override
    public String toString() {
        String recurringDescription = "";
        String recurringIcon = "";
        if (recurrencePeriod == RecurrencePeriod.DAILY) {
            recurringDescription = "every day";
            recurringIcon = "[R]";
        } else if (recurrencePeriod == RecurrencePeriod.WEEKLY) {
            recurringDescription = "every week";
            recurringIcon = "[R]";
        }
        return recurringIcon + key + "[" + this.getStatusIcon() + "] " + description + recurringDescription
                + (dateTime.isPresent()
                ? " (" + dateTime.get().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + ")"
                : "");
    }
}
