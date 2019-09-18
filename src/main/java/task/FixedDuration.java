package task;

public class FixedDuration extends Task {
    protected String needs;

    public FixedDuration(String description, String needs) {
        super(description);
        this.needs = needs;
    }

    @Override
    public String toString() {
        return "[F]" + super.toString() + " (needs: " + this.needs + ")";
    }

    @Override
    public String toWriteFile() {
        int boolToInt = isDone ? 1 : 0;
        return "F | " + boolToInt + " | " + this.description + " | " + this.needs + "\n";
    }
}

