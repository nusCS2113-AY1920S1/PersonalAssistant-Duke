package task;

import parser.DateTimeExtractor;

import java.io.Serializable;
import java.time.LocalDateTime;

import static parser.DateTimeExtractor.NULL_DATE;

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
     * @param startDate      end time of task
     * @param endDate      start time of task
     */
    public Event(String description, LocalDateTime startDate, LocalDateTime endDate) {
        super(description);
        this.endDate = endDate;
        this.startDate = startDate;
        this.remindInHowManyDays = 3;
    }

    /**
     * custom comparator for sorting.
     */
    @Override
    public int compareTo(Event o) {
        return this.startDate.compareTo(o.startDate);
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

        return "[E]" + "[" + super.getStatusIcon() + "] " + this.description + "(at: "
                + this.startDate.format(DateTimeExtractor.DATE_FORMATTER) + "-"
                + this.endDate.format(DateTimeExtractor.DATE_FORMATTER) + ")";
    }

    @Override
    boolean checkForClash(Task taskToCheck) {
        if (taskToCheck.endDate.isEqual(NULL_DATE)) {
            return (this.startDate.isBefore(taskToCheck.startDate) && this.endDate.isAfter(taskToCheck.startDate));
        } else {
            return this.startDate.isBefore(taskToCheck.endDate) && this.endDate.isAfter(taskToCheck.startDate);
        }
    }

}
