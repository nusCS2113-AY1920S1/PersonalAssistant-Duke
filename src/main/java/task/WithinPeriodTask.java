package task;

import java.io.Serializable;

/**
 * Task containing information of a Task to be done within a period.
 */
public class WithinPeriodTask extends Task implements Serializable {
    protected String start;
    protected String end;

    /**
     * Creates a WithinPeriodTask instance and initialises the required attributes.
     * @param description Description of the task.
     * @param start Start time of the task in format "dd/MM/yyyy HHmm".
     * @param end End time of the task in format "dd/MM/yyyy HHmm".
     */
    public WithinPeriodTask(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    /**
     * Returns a string status of the WithinPeriodTask task.
     * @return The task's status icon, description, start and end times.
     */
    @Override
    public String giveTask() {
        return "[W]" + super.giveTask() + "(between: " + start + " and " + end + ")";
    }
}
