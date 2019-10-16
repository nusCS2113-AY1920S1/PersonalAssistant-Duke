package executor.task;

public class FixedDuration extends Task {

    /**
     * Constructor for the 'Task' Class.
     *
     * @param name Name of the task as inputted by the user
     */
    public FixedDuration(String name) {
        super(name);
        this.taskType = TaskType.FDURATION;
        this.recordTaskDetails(name);
        this.detailDesc = "needs";
    }
}