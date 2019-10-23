package task;

import parser.DateTimeExtractor;

import java.io.Serializable;
import java.time.LocalDateTime;

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
        setReminder(3);
    }

    @Override
    public String toString() {
        String message = super.getPriorityIcon() + "[D]" + "[" + super.getStatusIcon() + "] " + this.description;
        String dateString = "(by: " + this.startDate.format(DateTimeExtractor.DATE_FORMATTER) + ")";
        if (!comment.isBlank()) {
            dateString = dateString + "  Note to self: " + comment;
        }
        return message.concat(dateString);
    }

    @Override
    public boolean isClash(Task taskToCheck) {
        if (taskToCheck.endDate == null) {
            return (this.startDate.isEqual(taskToCheck.startDate));
        } else {
            return (taskToCheck.startDate.isBefore(this.startDate) && taskToCheck.endDate.isAfter(this.startDate));
        }
    }

}
