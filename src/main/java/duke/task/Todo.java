package duke.task;

import duke.dukeexception.DukeException;

//@@author talesrune
/**
 * Represents a to-do that stores description.
 */
public class Todo extends Task {

    /**
     * Creates an event with the specified description and date/time.
     *
     * @param description The description of the task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Extracting a task content into readable string.
     *
     * @return String to be displayed.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Extracting a task content into readable string (GUI).
     *
     * @return String to be displayed.
     */
    @Override
    public String toStringGui() {
        return "[T]" + super.toStringGui();
    }

    /**
     * Extracting a task content into string that is suitable for text file.
     *
     * @return String to be written into text file.
     */
    @Override
    public String toFile() {
        return "T|" + super.toFile();
    }

    /**
     * Sets the date of the task.
     */
    @Override
    public void setDateTime(String dateTime) throws Exception {
        throw new DukeException("     Error! Todo does not have date/time.");
    }
}
