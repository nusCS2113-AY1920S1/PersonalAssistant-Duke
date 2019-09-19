package cube.task;

public class Recur extends Task{
    String frequency;
    /**
     * Constructor with two arguments.
     * Calls another constructor with additional parameter isDone=false.
     *
     * @param description the description of the task.
     * @param frequency the event that this task needs to be done after.
     */
    public Recur(String description, String frequency) {
        this(false, description, null);
        this.frequency = frequency;
    }

    /**
     * Constructor with three arguments.
     *
     * @param done true if the task is marked as done, otherwise false.
     * @param description the description of the task.
     * @param frequency the date of the deadline.
     */
    public Recur(boolean done, String description, String frequency) {
        super(description, null);
        this.isDone = done;
        this.frequency = frequency;
    }

    /**
     * Casts the task to String type.
     *
     * @return the String printout of the task
     */
    @Override
    public String toString() {
        return "[R]" + super.toString() + " (" + frequency + ")";
    }

    /**
     *  Get the frequency of this recurring task.
     * @return frequency
     */
    public String getFrequency() {
        return frequency;
    }

    //todo:to support recur in schedule after the scheduling extensions are implemented
}
