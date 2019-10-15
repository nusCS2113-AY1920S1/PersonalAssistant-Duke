package duke.task;

/**
 * Represents the ToDo task class.
 */

public class ToDo extends Item {
    /**
     * "taskDuration" is the date-time allocated to the task to be completed by.
     */
    private String taskDuration;

    /**
     * This method is the constructor used to create the todo class.
     *
     * @param info     This is the information about the task being added
     * @param status   This determines if whether
     *                 the Item added is completed or uncompleted
     * @param duration the duration to of the task
     */
    public ToDo(final String info,
                final Boolean status,
                final String duration) {
        super(info, status);
        super.setType("T");
        this.taskDuration = duration;
    }


    /**
     * This method is used to print out the type info and status of the Item.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString() + "(needs" + taskDuration + " hours)";
    }

    /**
     * Gets the length of time of the activity.
     */
    @Override
    public String getDuration () {
        return this.taskDuration;
    }
}
