package task;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * This extension of the task class will allow the user to add a task of to-do
 * type.
 *
 * @author Sai Ganesh Suresh
 * @version v2.0
 */
public class Todo extends Task implements Serializable {

    public Todo(String description) {
        super(description);
    }

    /**
     * Creates a ToDo task with a specific duration and timing.
     *
     * @param description description of task
     * @param at          start time of the task
     * @param to          end time of the task
     */
    public Todo(String description, LocalDateTime at, LocalDateTime to) {
        super(description);
        this.startDate = at;
        this.endDate = to;
        this.createdDate = LocalDateTime.now();
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
        String message = super.getPriorityIcon() + "[T]" + "[" + super.getStatusIcon() + "] " + this.description;
        if (!comment.isBlank()) {
            message = message + "  Note to self: " + comment;
        }
        return message;
    }

    @Override
    public boolean checkReminderTrigger() {
        return LocalDateTime.now().isAfter(createdDate.plusDays(remindInHowManyDays));
    }

    @Override
    boolean checkForClash(Task taskToCheck) {
        return false;
    }
}
