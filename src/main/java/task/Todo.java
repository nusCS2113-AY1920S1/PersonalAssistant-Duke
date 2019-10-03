package task;

import java.io.Serializable;

/**
 * Task containing information of a todo.
 */
public class Todo extends Task implements Serializable {

    /**
     * Creates a Todo instance and initialises the required attributes.
     * @param description Description of the todo.
     */
    public Todo(String description) {
        super(description);
        this.type = "T";
    }

    /**
     * Returns a string status of the Todo task.
     * @return The task's status icon and description.
     */
    @Override
    public String giveTask() {
        return "[T]" + super.giveTask();
    }
}
