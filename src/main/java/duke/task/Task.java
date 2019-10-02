package duke.task;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Superclass for all Tasks that will be added to the duke.task.Task Manager
 */
public class Task {
    enum RecurrencePeriod {NONE, DAILY, WEEKLY}

    protected String description;
    protected boolean isDone;
    protected String type;
    protected LocalDate dateCreated;
    protected RecurrencePeriod recurrencePeriod;

    /**
     * Constructor function for duke.task.Task
     * Creates a new instance of duke.task.Task by taking in a String description
     * Automatically flags the boolean isDone as False
     * Default Tasks have no type
     *
     * @param description the description of the task
     */
    public Task(String description, String recurrencePeriod) {
        this.description = description;
        this.isDone = false;
        this.type = "";
        this.dateCreated = LocalDate.now();
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
    }

    public Task(String description) {
    	this(description, "none");
	}

    /**
     * Returns a String object to show if a duke.task.Task has been marked done or not
     *
     * @return tick if done or X symbol if not done
     */
    public String getStatusIcon() {
        return (isDone ? "✓" : "✗");
    }

    /**
     * Flags the boolean attribute isDone as true in a task
     * Prints out the confirmation that the task is marked done
     */
    public void markAsDone() {
        isDone = true;
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(this);
    }

    public void markAsUndone() {
        isDone = false;
    }

    public void recurringTaskChecker() {
        switch (recurrencePeriod) {
            case DAILY:
                if (ChronoUnit.HOURS.between(dateCreated, LocalDate.now()) % 24 == 0) {
                    markAsUndone();
                }
                break;
            case WEEKLY:
                if (ChronoUnit.DAYS.between(dateCreated, LocalDate.now()) % 7 == 0) {
                    markAsUndone();
                }
                break;
            default:
                break;
        }
    }

    /**
     * Returns a String which describes the task
     *
     * @return the description of the task
     */
    public String getDescription() {
        return this.description;
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

        return recurringIcon + type + "[" + this.getStatusIcon() + "] " + description + recurringDescription;

    }
}
