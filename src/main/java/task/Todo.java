package task;
import process.*;
/**
 * Represents a Todo-typed-task
 */
public class Todo extends Task{
    /**
     * Creates a Todo object with the given description and whether it is done
     * @param description of the todo task
     * @param b if the task is checked or not
     * @throws DukeException if the Todo task is invalid
     */
    public Todo(String description, boolean b)  throws DukeException {
        super(description, b);
        super.tt = "T";
    }
    /**
     * Represents the object of this class as a string
     * @return that string
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
