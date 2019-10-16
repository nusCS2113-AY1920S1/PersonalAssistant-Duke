package cube.task;

public class DoAfter extends Task {
    private String afterEvent;

    /**
     * Constructor with two arguments.
     * Calls another constructor with additional parameter isDone=false.
     *
     * @param description the description of the task.
     * @param afterEvent the event that this task needs to be done after.
     */
    public DoAfter(String description, String afterEvent) {
        this(false, description, null);
        this.afterEvent = afterEvent;
    }

    /**
     * Constructor with three arguments.
     *
     * @param done true if the task is marked as done, otherwise false.
     * @param description the description of the task.
     * @param afterEvent the date of the deadline.
     */
    public DoAfter(boolean done, String description, String afterEvent) {
        super(description, null);
        this.isDone = done;
        this.afterEvent = afterEvent;
    }

    /**
     * Casts the task to String type.
     *
     * @return the String printout of the task
     */
    @Override
    public String toString() {
        return "[A]" + super.toString() + " (after: " + afterEvent + ")";
    }

    /**
     *  Get the event that this task must be done after.
     * @return afterEvent
     */
    public String getAfterEvent() {
        return afterEvent;
    }
}