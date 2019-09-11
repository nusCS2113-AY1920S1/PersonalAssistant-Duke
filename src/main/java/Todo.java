/**
 * Represents a specific {@link Task} todo, not necessarily indicating a deadline or a specific date
 */
public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
    @Override
    public String printInFile() {
        return this.isDone() ? "T|1|" + this.getDescription() : "T|0|" + this.getDescription();
    }
}