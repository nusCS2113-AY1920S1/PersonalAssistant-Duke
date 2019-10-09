package task;

import parser.DateTimeExtractor;

import java.io.Serializable;
import java.time.LocalDateTime;

import static parser.DateTimeExtractor.NULL_DATE;

/**
 * This extension of the task class will allow the user to add a task of
 * deadline type.
 *
 * @author Sai Ganesh Suresh
 * @version v2.0
 */
public class Deadline extends Task implements Serializable {

    /**
     * contructs a deadline task.
     *
     * @param description description of task
     * @param atDate      time of the task
     */
    public Deadline(String description, LocalDateTime atDate) {
        super(description);
        this.startDate = atDate;
        this.remindInHowManyDays = 3;
    }

    /**
     * This override of the toString function of the task class etches the different
     * portions of the user input into a single string.
     *
     * @return This function returns a string of the required task in the desired
     * output format of string type.
     */
    @Override
    public String toString() {
        return "[D]" + "[" + super.getStatusIcon() + "]" + this.description + "(by: "
            + this.startDate.format(DateTimeExtractor.DATE_FORMATTER) + ")";
    }

    @Override
    public boolean checkForClash(Task taskToCheck) {
        if (taskToCheck.endDate.isEqual(NULL_DATE)) {
            return (this.startDate.isEqual(taskToCheck.startDate));
        } else {
            return (taskToCheck.startDate.isBefore(this.startDate) && taskToCheck.endDate.isAfter(this.startDate));
        }
    }
}
