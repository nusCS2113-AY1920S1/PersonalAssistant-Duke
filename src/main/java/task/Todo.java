package task;

/**
 * Represents a Todo. A Todo is a task without time information.
 */
public class Todo extends Task {

    /**
     * Constructor for Todo.
     *
     * @param description the description of the Todo.
     */
    public Todo(String description) {
        super(description);
    }

    public String toString() {
        return String.format(super.toString(), "\uD83D\uDCCB");
    }
}
