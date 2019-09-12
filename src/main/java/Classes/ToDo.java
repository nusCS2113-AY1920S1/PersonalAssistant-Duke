/**
 * Class representing a task to be done
 * Subclass of Task Class
 */
public class ToDo extends Task {
    /**
     * Constructor that takes in the task to be completed
     * @param description String representing the task to be completed
     */
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}