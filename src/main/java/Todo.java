public class Todo extends Task {
    /**
     * Consructor for todo
     * @param description of the task
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + "[" + super.getStatusIcon() + "] " + super.description;
    }
}