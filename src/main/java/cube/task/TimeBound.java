package cube.task;

public class TimeBound extends Task{
    private String timeBound;

    /**
     * Constructor with two arguments.
     * Calls another constructor with additional parameter isDone = false.
     *
     * @param description the description of the task.
     * @param timeBound the time period within which the task has to be completed.
     */

    public TimeBound(String description, String timeBound) {
        this(false, description, null);
        this.timeBound = timeBound;
    }

    /**
     * Constructor with three arguments.
     *
     * @param done true if the task is marked as done, otherwise false.
     * @param description the description of the task.
     * @param timeBound the time period within which the task has to be completed.
     */

    public TimeBound(boolean done, String description, String timeBound) {
        super(description, null);
        this.isDone = done;
        this.timeBound = timeBound;
    }

    /**
     * Casts the task to String type.
     *
     * @return the String printout of the task
     */

    @Override
    public String toString() {
        return "[TB]" + super.toString() + " (Time period: " + timeBound + ")";
    }

    /**
     * Get the time period of this task.
     *
     * @return timeBound;
     */

    public String getTimeBound() {
        return timeBound;
    }
}