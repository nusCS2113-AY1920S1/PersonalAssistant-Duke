package model.tasks;

/**
 * Provide support for managing tasks that takes a fixed amount of time but does not have a fixed start/end time
 * e.g., reading the sales report (needs 2 hours).
 * command is "add last [description] /last [duration]"
 */

public class Last extends Task {

    /**
     * a String store the duration time
     */
    private String duration;

    /**
     * default constructor of Last
     */
    public Last() {
    }


    /**
     * another constructor of Last
     *
     * @param description description of this task
     * @param duration    duration of this task
     */
    public Last(String description, String duration) {
        super(description);
        this.duration = duration;
    }


    /**
     * a method to set the duration
     *
     * @param duration duration of this task
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    /**
     * a method to format the output of the task list
     * @return a string which will be printed out in console when listing this Last task
     */
    public String toString() {
        return "[L]" + super.toString() + " (last: " + duration + ")";
    }

    @Override
    /**
     * a method to format the data list data store in file
     * @return a string which will show in data file that store the task list
     */
    public String dataString() {
        return "L | " + (this.isDone ? 1 : 0) + " | " + this.description + " | " + this.duration;
    }

}
