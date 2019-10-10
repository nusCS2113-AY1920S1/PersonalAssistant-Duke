package duke.items.tasks;

public class After extends Task {
    private String doAfter;

    /**
     * Constructor for Duke.Tasks.After object.
     * @param taskName name or description of the after task.
     * @param doAfter the time or task for the current task to be completed after.
     */
    public After(String taskName, String doAfter) {
        super(taskName);
        this.doAfter = doAfter;
    }

    /**
     * This constructor is used for recreation of Duke.Tasks.After from storage.
     * @param done 1 if task has been marked complete, 0 otherwise.
     * @param taskName the name or description of the after task.
     * @param doAfter the time or task for the current task to be completed after.
     */
    public After(int done, String taskName, String doAfter) {
        super(taskName);
        this.setDone(done == 1);
        this.doAfter = doAfter;
    }

    @Override
    public String toString() {
        return "[A]" + super.toString() + " (after: " + getDoAfter() + ")";
    }

    @Override
    public String storeString() {
        return "A | " + super.storeString() + " | " + getDoAfter();
    }

    @Override
    String getStartDateString() {
        return null;
    }

    @Override
    String getEndDateString() {
        return null;
    }

    public String getDoAfter() {
        return this.doAfter;
    }
}