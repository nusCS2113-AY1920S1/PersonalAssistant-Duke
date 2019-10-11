package Tasks;
/**
 * Represents a task called to-do.
 */
public class Todo extends Task {

    /**
     * Creates a To-do object.
     * @param description Description of a task
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String getType() {
        return "[T]";
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String getModCode() {
        return null;
    }
}
