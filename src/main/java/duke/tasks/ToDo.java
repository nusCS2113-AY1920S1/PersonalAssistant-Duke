package duke.tasks;

/**
 * Task is a public class that extends from Task
 */
public class ToDo extends Task {

    /**
     * This is the constructor of ToDo object
     * @param description the description of the todo object
     */
    public ToDo(String description) {
        super(description);
        super.type = "T";
    }

    /**
     * this function overrides the toString() function in Task to represents the full description of a ToDo object
     * @return <code>"[T]" + super.toString()</code>
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
