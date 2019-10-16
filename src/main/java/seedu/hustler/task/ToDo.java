package seedu.hustler.task;

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
     * Initializes description and default difficulty.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Initializes description and user input difficulty.
     */
    public ToDo(String description, String difficulty, String tag, LocalDateTime now) {
        super(description, difficulty, tag, now);
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
     * with inherited task string saveAchievements format.
     *
     * @return inherited string plus task format.
     */
    public String toSaveFormat() {
        return "T|" + super.toSaveFormat() + super.toSaveInputDateTime();
    }

    /**
     * Checks equality with another Todo instance.
     *
     * @param temp the instance to compare against.
     * @return true or false to the comparison.
     */
    public boolean equals(ToDo temp) {
        return this.description.equals(temp.description);
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
