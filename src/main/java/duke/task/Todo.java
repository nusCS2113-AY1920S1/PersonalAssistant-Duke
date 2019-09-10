package duke.task;

import duke.exception.DukeException;

/**
 * Class representing a task to be done, but not at any particular time.
 */
public class Todo extends Task {

    /**
     * Initializes a todo based on the description.
     *
     * @param description The todo description that the user expects to do.
     */
    Todo(String description) {
        super(description);
    }

    /**
     * Creates this instance of a todo object.
     *
     * @param description The raw data to be checked if it is empty.
     *
     * @return a new todo task that has a description
     * @throws DukeException when any of the parsing fails to conform with standards.
     */
    public static Todo create(String description) throws DukeException {
        if (description.isEmpty() || description.isBlank()) {
            throw new DukeException("The description of a todo cannot be empty or blank spaces only");
        }
        description = description.trim();
        return new Todo(description);
    }

    /**
     * Returns a string representation of this todo.
     *
     * @return The desired string representation with more elaborated date formatting.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Exports this todo for saving to disk.
     *
     * @return A string representation of this event containing the task type "T".
     */
    @Override
    public String export() {
        return "T | " + super.export() + super.getDescription().length() + " | " + super.getDescription();
    }
}
