package spinbox.entities.items.tasks;

import spinbox.entities.items.Item;

public abstract class Task extends Item {
    protected static final String DELIMITER_FILTER = " \\| ";
    /**
     * Constructor to initialize default values of any instances of children of Task.
     */
    TaskType taskType = null;

    public Task(String taskName) {
        super(taskName);
    }

    public Task() {

    }

    public TaskType getTaskType() {
        return taskType;
    }

    public abstract boolean isSchedulable();
}
