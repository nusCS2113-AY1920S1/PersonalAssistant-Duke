package seedu.duke.task;

import java.time.LocalDateTime;

/**
 * A class that inherits from the abstract class Task. This task type
 * is a simple todo that can be marked as done when done.
 */
public class ToDo extends Task {
    /**
     * LocalDateTime to store the todo's date and time.
     */
    protected LocalDateTime localDateTime;

    /**
     * Initializes description.
     */
    public ToDo(String description) {
        super(description);
        this.localDateTime = null;
    }

    /**
     * Overrides the method to display the task type along
     * with inherited task string.
     *
     * @return details of the task in a user readable format.
     */
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Overrides the method to display the task type along
     * with inherited task string save format.
     *
     * @return inherited string plus task format.
     */
    public String toSaveFormat() {
        return "T|" + super.toSaveFormat();
    }

    @Override
    public LocalDateTime getDateTime() {
        return this.localDateTime;
    }

    @Override
    public void setDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
