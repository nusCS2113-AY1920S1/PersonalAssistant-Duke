package Model_Classes;

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
     * Returns a string with the full description of the task
     * @return A string with the task type and its description.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
