package seedu.duke.task;

import java.time.LocalDateTime;

/**
 * A class that inherits from the abstract class Task. This task type
 * is a simple todo that can be marked as done when done.
 */
public class ToDo extends Task {
    /**
     * Initializes description.
     */
    public ToDo(String description) {
        super(description);
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

    /**
     * Overrides the getDateTime method in Task to obtain the todo's date and time.
     *
     * @return date and time of todo of type LocalDateTime.
     */
    @Override
    public LocalDateTime getDateTime() {
        return null; //currently empty as todo currently does not have date and time to it
    }

    /**
     * Overrides the setDateTime method in Task to set the todo's date and time.
     *
     * @param dateTime the date and time of the todo of type LocalDateTime.
     */
    @Override
    public void setDateTime(LocalDateTime dateTime) {
        //currently empty as todo currently does not have date and time to it
    }

    /**
     * Overrides the setDateTime method in Task to set the todo's date and time.
     *
     * @param dateTime string of the date and time of the todo.
     */
    @Override
    public void setDateTime(String dateTime) {
        //currently empty as todo currently does not have date and time to it
    }
}
