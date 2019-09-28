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

    /**
     * Customises the toString() method to print the Todo object.
     *
     * @return Customised String for Todo object.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
