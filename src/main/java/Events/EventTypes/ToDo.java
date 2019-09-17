package Events.EventTypes;

import main.java.Events.EventTypes.Task;

/**
 * Subclass of Model_Class.Task class.
 * Type of class available for use within Duke program, not time-specific.
 */
public class ToDo extends Task {

    /**
     * Creates new Model_Class.ToDo object.
     *
     * @param description description of task
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Creates new Model_Class.ToDo object.
     * Contains boolean signifying task completion so as to facilitate reading from save file.
     *
     * @param description task description
     * @param isDone      task completion
     */
    public ToDo(String description, boolean isDone) {
        super(description, isDone);
    }

    /**
     * Gets task in string format.
     *
     * @return String containing type of task and task description.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    public String getDate() {
        return null;
    }
}
