package duke.tasks;

public class FixedDurationTask extends Task {
    private String duration;

    /**
     * Constructor for the duke.tasks.FixedDurationTask Object
     *
     * @param description Name of the fixedDurationTask
     * @param duration Duration of the Task
     */
    public FixedDurationTask(String description, String duration) {
        super(description);
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "[F]" + super.toString() + " (needs " + duration + ")";
    }
}
