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
    public void setReminder(int days) {
        reminder = new Reminder(days);
    }

    @Override
    boolean checkForClash(Task taskToCheck) {
        return false;
    }
}
