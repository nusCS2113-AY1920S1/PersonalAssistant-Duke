package task;

/**
 * Represents the object that manages todo tasks.
 * Inherits from Task class.
 */
public class Todo extends Task {

    public Todo(String description) {
        super(description);
        this.type = "T";
    }

    public Todo(String description, boolean state) {
        super(description);
        this.isDone = state;
        this.type = "T";
    }
}