/**
 * Represents a Todo task. Stores a description and whether it is done.
 */
public class Todo extends Task {
    /**
     * Constructor for a new Todo. Called when a Todo is first created.
     * @param description a String description of the Todo.
     */
    public Todo(String description) {
        super(description);
        this.type = 'T';
    }

    /**
     * Constructor for an existing Todo from our saved tasks. Called to
     * create saved tasks.
     * @param description a String description of the Todo.
     * @param isDone a Boolean indicating whether the task has been fulfilled.
     */
    public Todo(String description, boolean isDone) {
        super(description, isDone);
        this.type = 'T';
    }
}