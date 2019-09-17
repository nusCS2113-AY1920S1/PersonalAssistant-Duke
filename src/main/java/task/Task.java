package task;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;

/**
 * This Task class is extended by the other tasks and serves as a template for all tasks.
 *
 * @serial
 * @author Sai Ganesh Suresh
 * @version v2.0
 */
public class Task implements Serializable{

    public String description; // basically similar to describing features of the class
    protected boolean isDone;

    public LocalDateTime fromDate;
    public LocalDateTime toDate;
    public LocalDateTime atDate;
    public Period eventPeriod;
    public int remindInHowManyDays = 0;

    /**
     * This task constructor is used to obtain the parameters required by the task class.
     *
     * @param description This string holds the description provided by the user.
     */
    public Task(String description) { // constructor
        this.description = description;
        this.isDone = false;
    }

    /**
     * This getStatusIcon function returns the tick or cross symbols to be printed as output
     *
     * @return This function returns either a tick or a cross.
     */
    public String getStatusIcon() { // return tick or X symbols
        return (isDone ? "\u2713" : "\u2718");
    }

    /**
     * This markAsDone function allows the user to mark a task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * This toString function of the task class etches the different portions of the user input into a
     * single string.
     *
     * @return This function returns a string of the required task in the desired output format of string type.
     */
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    public void setReminder(int days){
        this.remindInHowManyDays = days;
    }

    public boolean checkReminderTrigger() {
        if (atDate != null) {
            LocalDateTime reminderDate = atDate.minusDays(remindInHowManyDays);
            return LocalDateTime.now().isAfter(reminderDate);
        }
        if (fromDate != null) {
            LocalDateTime reminderDate = fromDate.minusDays(remindInHowManyDays);
            return LocalDateTime.now().isAfter(reminderDate);
        }
        return false;
    }

}