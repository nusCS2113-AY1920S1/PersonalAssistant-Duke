package duke.task;

/**
 * Represents a <code>Task</code> object. A <code>Todo</code>
 * object is a type of <code>Task</code>.
 */
public class Todo extends Task {
    /**
     * Constructor for <code>Todo</code>.
     * @param description Description of the <code>Todo</code> object.
     */
    public Todo(String description) {
        super(description);
    }
    /**
     * Customises the <code>toString()</code> method to print
     * the <code>Todo</code> object.
     * @return Customised String for <code>Todo</code> object.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
