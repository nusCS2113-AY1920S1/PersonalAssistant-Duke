package oof.task;

/**
 * A Todo object is a type of Task.
 */
public class Todo extends Task {

    /**
     * Constructor for Todo.
     *
     * @param description Description of the Todo object.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
