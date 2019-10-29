package oof.model.task;

/**
 * A Todo object is a type of Task.
 */
public class Todo extends Task {

    private String todoDate;

    /**
     * Constructor for Todo.
     *
     * @param description Description of the Todo object.
     */
    public Todo(String description, String todoDate) {
        super(description);
        this.todoDate = todoDate;
    }

    public String getTodoDate() {
        return todoDate;
    }

    /**
     * Converts a task object to string format for storage.
     * @return Task object in string format for storage.
     */
    @Override
    public String toStorageString() {
        return "TODO" + DELIMITER + getStatusIcon() + DELIMITER + description + DELIMITER + todoDate
                + DELIMITER + DELIMITER + DELIMITER + DELIMITER;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString() + " (on: " + todoDate + ")";
    }
}
