package Tasks;
public class FixedDuration extends Task {
    private String duration;
    public FixedDuration(final String description, final String duration) {
        super(description);
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "FD" + "|" + super.getStatusIcon() + "|" + super.description + "|" + duration;
    }
    @Override
    public String listFormat() {
        return "[FD]" + "[" + super.getStatusIcon() + "] " + super.description + "(requires:" + duration + ")";
    }

}