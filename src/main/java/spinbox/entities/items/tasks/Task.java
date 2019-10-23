package spinbox.entities.items.tasks;

import spinbox.entities.items.Item;

public abstract class Task extends Item {
    /**
     * Constructor to initialize default values of any instances of children of Task.
     */
    TaskType taskType = null;

    public Task(String taskName) {
        super(taskName);
    }

    @Override
    public String storeString() {
        return super.storeString();
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public abstract boolean isSchedulable();
}
