package oof.model.task;

/**
 * A Todo object is a type of Task.
 */
public class Todo extends Task {

    private String on;

    /**
     * Constructor for Todo.
     *
     * @param description Description of the Todo object.
     */
    public Todo(String description, String on) {
        super(description);
        this.on = on;
    }

    public String getOn() {
        return on;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString() + " (on: " + on + ")";
    }
}
