package cube.task;

public class FixedDuration extends Task {
    private String fixedDuration;

    /**
     * Constructor with two arguments.
     * Calls another constructor with additional parameter isDone = false.
     *
     * @param description the description of the task.
     * @param fixedDuration the duration of this task.
     */

    public FixedDuration(String description, String fixedDuration) {
        this(false, description, null);
        this.fixedDuration = fixedDuration;
    }

    /**
     * Constructor with three arguments.
     *
     * @param done true if the task is marked as done, otherwise false.
     * @param description the description of the task.
     * @param fixedDuration the duration of the task.
     */

    public FixedDuration(boolean done, String description, String fixedDuration) {
        super(description, null);
        this.isDone = done;
        this.fixedDuration = fixedDuration;
    }

    /**
     * Casts the task to String type.
     *
     * @return the String printout of the task
     */

    @Override
    public String toString() {
        return "[FD]" + super.toString() + " (Duration: " + fixedDuration + ")";
    }

    /**
     * Get the duration of this task.
     *
     * @return fixedDuration
     */
    public String getAfterEvent() {
        return fixedDuration;
    }
}