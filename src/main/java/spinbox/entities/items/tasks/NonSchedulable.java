package spinbox.entities.items.tasks;

public abstract class NonSchedulable extends Task {

    /**
     * Constructor to initialize default values of any instances of children of Task.
     *
     * @param taskName the name of task.
     */
    public NonSchedulable(String taskName) {
        super(taskName);
    }

    public boolean isSchedulable() {
        return false;
    }
}
