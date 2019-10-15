package spinbox.items.tasks;

import spinbox.DateTime;
import spinbox.items.Item;

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

    public abstract boolean isSchedulable();
}
