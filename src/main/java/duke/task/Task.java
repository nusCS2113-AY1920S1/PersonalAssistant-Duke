package duke.task;

import duke.core.DateTimeParser;
import duke.core.DukeException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * Represents a task.  Task is an abstract class that can not be
 * instantiated
 */
public abstract class Task {

    /**
     * A String that represents the description of the task.
     */
    protected String description;

    /**
     * A boolean that represents the status of the task( 1 means done, 0 means not yet)
     */
    protected boolean isDone;

    /**
     * a localDateTime constructor to save the date and time
     */
    protected LocalDateTime ld = null;

    /**
     * A boolean that represents whether or not a task is recurring. True = recurring, False = non-recurring
     */
    protected boolean isRecurring = false;

    /**
     * An enumerator meant to specify the frequency of a recurring task.
     */
    public enum RecurringFrequency { DAILY, WEEKLY, MONTHLY, ONCE; }

    /**
     * An extended class that retains information about a recurring task.
     */
    protected RecurringTask recurringTask;

    /**
     * Initialises the minimum fields required to setup a Task.
     *
     * @param description A string that represents the description of certain task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns an icon that represents the status of the task.
     *
     * @return Tick if completed, cross if uncompleted.
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    /**
     * Check if the task isDone
     *
     * @return boolean value of isDone
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Returns a string with the following format to be stored in a local file
     *
     * @return A string in a specific format to be stored in a local file.
     */
    public abstract String writeTxt();

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks the task as recurring.
     */
    public void makeTaskRecurring(RecurringFrequency frequency) {
        isRecurring = true;
        switch (frequency) {
            case DAILY:
                this.recurringTask = new RecurringTask(this, RecurringTask.RecurringFrequency.DAILY);
                break;
            case WEEKLY:
                this.recurringTask = new RecurringTask(this, RecurringTask.RecurringFrequency.WEEKLY);
                break;
            case MONTHLY:
                this.recurringTask = new RecurringTask(this, RecurringTask.RecurringFrequency.MONTHLY);
                break;
            case ONCE:
                break;
        }
        if (this.recurringTask != null) {
            this.recurringTask.recurringTaskTimeUpdate(this);
        }
    }

    /**
     * Returns boolean stating whether task is recurring.
     */
    public boolean isTaskRecurring() { return isRecurring; }

    /**
     * Returns a string with the status icon and the description of the task.
     *
     * @return A string in a specific format with the status and description of the task.
     */
    public String printStatus() {
        return "[" + this.getStatusIcon() + "] " + description;
    }

    /**
     * Returns the description of the task.
     *
     * @return A string that represents the specific activity associated with
     * the task.
     */
    public String getDescription() {
        return description;
    }



//    /**
//     * Returns a string that representing the data and time for the task
//     * in a predefined date time format.
//     * @param timeBeforeFormat a string that provides the data and time information.
//     * @return A string that represents the specific activity associated with
//     * the task.
//     */
//    public String timeFormatter(String timeBeforeFormat) {
//        DateTimeFormatter stFormatter = DateTimeFormatter.ofPattern("d'st of' MMMM yyyy, ha");
//        DateTimeFormatter ndFormatter = DateTimeFormatter.ofPattern("d'nd of' MMMM yyyy, ha");
//        DateTimeFormatter rdFormatter = DateTimeFormatter.ofPattern("d'rd of' MMMM yyyy, ha");
//        DateTimeFormatter thFormatter = DateTimeFormatter.ofPattern("d'th of' MMMM yyyy, ha");
//
//        String output;
//
//        if ((ld.getDayOfMonth() % 10) == 1) {
//            output = ld.format(stFormatter);
//        } else if ((ld.getDayOfMonth() % 10) == 2) {
//            output = ld.format(ndFormatter);
//        } else if ((ld.getDayOfMonth() % 10) == 3) {
//            output = ld.format(rdFormatter);
//        } else {
//            output = ld.format(thFormatter);
//        }
//        return output;
//    }

    /**
     * update the <code>LocalDateTime</> constructor to save the date and time
     * @param newDateTime the time retrieved from user input.
     */
    public void updateLocalDateTime(String newDateTime) throws DukeException {
        ld = DateTimeParser.convertToLocalDateTime(newDateTime);
    }

    /**
     * Returns the data and time information stored in the task without a certain format.
     *
     * @return A LocalDateTime Variable that contains the date and time information.
     */
    public LocalDateTime getDateTime()
    {
        if (recurringTask != null) {
            this.ld = recurringTask.recurringTaskTimeUpdate(this);
        }
        return ld;
    }

    public LocalDate getDate() {
        return ld.toLocalDate();
    }
}