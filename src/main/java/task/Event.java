package task;

import parser.DateTimeExtractor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This extension of the task class will allow the user to add a task of event
 * type.
 *
 * @author Sai Ganesh Suresh
 * @version v2.0
 */
public class Event extends Task implements Serializable, Comparable<Event> {

    /**
     * creates a new Event task.
     *
     * @param description description of task
     * @param startDate   end time of task
     * @param endDate     start time of task
     */
    public Event(String description, LocalDateTime startDate, LocalDateTime endDate) {
        super(description);
        this.endDate = endDate;
        this.startDate = startDate;
        setReminder(3);
    }

    /**
     * Custom comparator for sorting.
     */
    @Override
    public int compareTo(Event o) {
        return this.startDate.compareTo(o.startDate);
    }

    @Override
    public String toString() {
        String message = super.getPriorityIcon() + "[E]" + "[" + super.getStatusIcon() + "] " + this.description;
        String dateString = "(at: " + this.startDate.format(DateTimeExtractor.DATE_FORMATTER) + "-"
                + this.endDate.format(DateTimeExtractor.DATE_FORMATTER) + ")";
        if (!comment.isBlank()) {
            dateString = dateString + "  Note to self: " + comment;
        }
        return message.concat(dateString);
    }

    @Override
    boolean isClash(Task taskToCheck) {
        if (taskToCheck.endDate == null) {
            return (this.startDate.isBefore(taskToCheck.startDate) && this.endDate.isAfter(taskToCheck.startDate));
        } else {
            return this.startDate.isBefore(taskToCheck.endDate) && this.endDate.isAfter(taskToCheck.startDate);
        }
    }

}
