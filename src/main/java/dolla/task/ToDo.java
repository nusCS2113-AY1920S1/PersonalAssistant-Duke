package dolla.task;

/**
 * duke.task.ToDo is a type of duke.task.Task that only stores the task description
 */
public class ToDo extends Task {
    /**
     * Creates an instance with specified task description.
     * @param description String of what the task entails.
     */
    public ToDo(String description) {
        super(description);
        type = 'T';
    }

}
