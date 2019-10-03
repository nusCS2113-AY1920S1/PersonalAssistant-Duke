package task;

import java.io.Serializable;

/**
 * Task containing information of a deadline.
 */
public class DoAfterTasks extends Task implements Serializable {
    //protected String at;

    /**
     * Creates a DoAfterTasks instance and initialises the required attributes.
     * @param description Description of the DoAfterTasks.
     * @param after Time of the DoAfterTasks in format "dd/MM/yyyy HHmm".
     */
    public DoAfterTasks(String description, String after) {
        super(description);
        this.after = after;
        this.type = "A";
    }

    /**
     * Returns a string status of the Event task.
     * @return The task's status icon, description and eventtime.
     */
    @Override
    public String giveTask() {
        return "[A]" + super.giveTask() + "(after: " + after + ")";
    }


}
