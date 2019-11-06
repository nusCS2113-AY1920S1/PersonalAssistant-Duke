package chronologer.task;

import java.time.LocalDateTime;

import chronologer.parser.DateTimeExtractor;

import java.io.Serializable;

/**
 * This extension of the task class will allow the user to add a task of to-do
 * type.
 *
 * @author Sai Ganesh Suresh
 * @version v2.0
 */
public class Todo extends Task implements Serializable {

    private static final String TODO = "TODO";
    private static final String TODO_DURATION = "TODO DURATION";
    private static final String TODO_PERIOD = "TODO PERIOD";

    public int duration = 0;

    public Todo(String description) {
        super(description);
        this.type = TODO;
    }

    /**
     * Creates a ToDo task with a specific duration.
     *
     * @param description description of task
     * @param duration   duration of the task
     */
    public Todo(String description, int duration) {
        super(description);
        this.duration = duration;
        this.type = TODO_DURATION;
    }

    /**
     * Creates a ToDo task with a specific period and timing.
     *
     * @param description description of task
     * @param startDate   start time of the task
     * @param endDate     end time of the task
     */
    public Todo(String description, LocalDateTime startDate, LocalDateTime endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = TODO_PERIOD;
    }

    /**
     * This override of the toString function of the task class etches the different
     * portions of the user input into a single string.
     *
     * @return This function returns a string of the required task in the desired
     *         output format of string type.
     */
    @Override
    public String toString() {
        String message = "";
        if (this.duration == 0 && this.startDate == null) {
            message = super.getPriorityIcon() + "[T]" + "[" + super.getStatusIcon() + "] " + this.description;
        } else if (this.duration == 0) {
            message = super.getPriorityIcon() + "[T]" + "[" + super.getStatusIcon() + "] " + this.description
                + " (from: " + this.startDate.format(DateTimeExtractor.DATE_FORMATTER) + ")" + " (to: "
                + this.endDate.format(DateTimeExtractor.DATE_FORMATTER) + ")";

        } else {
            message = super.getPriorityIcon() + "[T]" + "[" + super.getStatusIcon() + "] " + this.description + " "
                + "(for " + duration + " hours)";
        }
        if (!location.isBlank()) {
            message = message + "\n" + location;
        }
        if (!comment.isBlank()) {
            message = message + "\nNote to self: " + comment;
        }
        return message;
    }

    @Override
    public void setReminder(int days) {
        reminder = new Reminder(days);
    }

    @Override
    boolean isClash(Task taskToCheck) {
        return false;
    }

    public int getDuration() {
        return duration;
    }
}
