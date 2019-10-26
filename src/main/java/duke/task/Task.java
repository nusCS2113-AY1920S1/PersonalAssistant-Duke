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

    private String description;

    private int duration;
    private Optional<String> filter;
    protected Optional<LocalDateTime> dateTime;
    private Optional<String> recurrencePeriod;

    protected boolean isDone;
    protected String key;
    protected LocalDateTime createdDate;
    protected Priority priority;

    /**
     * Constructor function for duke.task.Task
     * Creates a new instance of duke.task.Task by taking in a String description
     * Automatically flags the boolean isDone as False
     * Default Tasks have no type
     *
     * @param description the description of the task
     */

    public Task(Optional<String> filter, Optional<LocalDateTime> dateTime, Optional<String> recurrencePeriod, String description, int duration) {
        this.description = description;
        this.isDone = false;
        this.key = "";
        this.createdDate = LocalDateTime.now();
        this.priority = Priority.LOW;
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
        if ("daily".equals(recurrencePeriod)) {
            return "D";
        } else if ("weekly".equals(recurrencePeriod)) {
            return "W";
        }
        return "N";
    }

    public Optional<String> getFilter() {
        return this.filter;
    }

    /**
     * This function marks tasks as undone every week/day based on the
     * recurrence period of the task.
     */
//    public boolean isTimeToReset(LocalDate dateCreated, LocalDate dateNow) {
//        switch (recurrencePeriod) {
//            case DAILY:
//                if (ChronoUnit.DAYS.between(dateCreated, dateNow) > 0) {
//                    return true;
//                } else {
//                    return false;
//                }
//            case WEEKLY:
//                if (ChronoUnit.DAYS.between(dateCreated, dateNow) > 7) {
//                    return true;
//                } else {
//                    return false;
//                }
//            default:
//                return false;
//        }
//    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setDescription(String description) {
        this.description = description;
        System.out.println(this + " description has been successfully updated");
    }

    public void setDateTime(Optional<LocalDateTime> dateTime) {
        this.dateTime = dateTime;
        System.out.println(this + " has a new deadline of " + dateTime.get().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }

    public void setDuration(int duration) {
        this.duration = duration;
        System.out.println(this + " has a new duration of " + duration + ((duration == 1) ? "hour" : " hours"));
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
        String recurringDescription = "every week";
        String recurringIcon = "";
//        if (recurrencePeriod == RecurrencePeriod.DAILY) {
//            recurringDescription = "every day";
//            recurringIcon = "[R]";
//        } else if (recurrencePeriod == RecurrencePeriod.WEEKLY) {
//            recurringDescription = "every week";
//            recurringIcon = "[R]";
//        }
        return recurringIcon + key + "[" + this.getStatusIcon() + "] " + description + recurringDescription
                + (dateTime.isPresent()
                ? " (" + dateTime.get().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + ")"
                : "");
    }
}
