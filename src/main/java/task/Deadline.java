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
     * Constructor for deadline task.
     *
     * @param description Description of the deadline
     * @param atDate      Due date for deadline
     */
    public Deadline(String description, LocalDateTime atDate) {
        super(description);
        this.startDate = atDate;
        this.remindInHowManyDays = 3;
    }

    @Override
    public String toString() {
        String message = super.getPriorityIcon() + "[D]" + "[" + super.getStatusIcon() + "] " + this.description;
        String dateString = "(by: " + this.startDate.format(DateTimeExtractor.DATE_FORMATTER) + ")";
        return message.concat(dateString);
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
