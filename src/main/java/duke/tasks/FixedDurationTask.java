package duke.tasks;

public class FixedDurationTask extends ToDo {

    private String duration;

    public FixedDurationTask(String description, String duration) {
        super(description);
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "[T]" + description + " (needs " + duration + ")";
    }

    @Override
    public String getFullString() {
        return "[T][" + getStatusIcon() + "] " + description + " (needs " + duration + ")";
    }

}
