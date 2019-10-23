package duke.task;

public class FixedDuration extends Tasks {
    private String duration;
    public FixedDuration(String description, String type, String duration) {
        super(description, type);
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    public String toMessage() {
        return description
                + " (duration: " + duration + ")";
    }
}
