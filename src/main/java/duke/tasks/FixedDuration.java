package duke.tasks;

public class FixedDuration extends Task {
    protected String duration;

    public FixedDuration(String description, String duration) {
        super(description);
        this.duration = duration;
        super.type ="FD";
    }

    @Override
    public String toString() {
        return "[FD]" + super.toString() + " (needs: " + duration + ")";
    }
}
