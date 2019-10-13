package Model_Classes;

import Enums.Priority;

/**
 * An object class that stores the description of tasks that needs to be done with no deadline.
 */
public class ToDo extends Task {
    /**
     * Constructor of the class. Takes in the description of the task
     * @param description Description of the task.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Overload constructor of the class
     * Takes in the user at which the task is assigned to
     * @param description Description of the task
     * @param user User whom the task is assigned to
     */
    public ToDo(String description, String user) {
        super(description, user);
    }

    /**
     * Overload constructor of the class. Takes in the description of the task
     * @param description Description of the task.
     * @param done Whether the task is completed.
     * @param priority Priority of the task.
     */
    public ToDo(String description, boolean done, Priority priority) {
        super(description, done, priority);
    }

    /**
     * Returns a string with the full description of the task
     * @return A string with the task type and its description.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
