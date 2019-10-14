package spinbox.items.tasks;

public class Fixed extends Task {
    private String fixedDuration;

    /**
     * Constructor for SpinBox.Tasks.Fixed object.
     * @param taskName name or description of the fixed duration task.
     * @param fixedDuration duration of the fixed duration task.
     */
    public Fixed(String taskName, String fixedDuration) {
        super(taskName);
        this.fixedDuration = fixedDuration;
    }

    /**
     * This constructor is used for recreation of SpinBox.Tasks.Fixed from storage.
     * @param done 1 if task has been marked complete, 0 otherwise.
     * @param taskName the name or description of the fixed duration task.
     * @param fixedDuration duration of the fixed duration task.
     */
    public Fixed(int done, String taskName, String fixedDuration) {
        super(taskName);
        this.setDone(done == 1);
        this.fixedDuration = fixedDuration;
    }

    @Override
    public String toString() {
        return "[F]" + super.toString() + " (needs: " + getFixedDuration() + ")";
    }

    @Override
    public String storeString() {
        return "F | " + super.storeString() + " | " + getFixedDuration();
    }

    @Override
    String getStartDateString() {
        return null;
    }

    @Override
    String getEndDateString() {
        return null;
    }

    public String getFixedDuration() {
        return this.fixedDuration;
    }
}