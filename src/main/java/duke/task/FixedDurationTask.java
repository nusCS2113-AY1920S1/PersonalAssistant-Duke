package duke.task;

public class FixedDurationTask extends Task {
    /**
     * A string that represents the duration of the task
     */
    private String duration;

    /**
     * Constructs a Fixed duration task object. Duration is parsed and
     * stored in string format
     *
     * @param description A string that saves the description of the task.
     * @param duration    A string that specifies the duration of the task.
     */
    public FixedDurationTask(String description, String duration) {
        super(description);
        this.duration = duration;
    }

    /**
     *
     * @return A string which displays the type,
     * description and duration of the task.
     */
    @Override
    public String toString() {
        return "[F]" + super.printStatus() + " (duration: " + this.duration + ")";
    }

    /**
     * Returns a string with the following format to be stored in a local file
     *
     * @return A string in a specific format to be stored in a local file.
     */
    public String writeTxt() {
        return "F | "
                + (this.isDone() ? "1" : "0")
                + " | "
                + this.description
                + " | "
                + this.duration
                + " | "
                + this.isRecurring;
    }
}
