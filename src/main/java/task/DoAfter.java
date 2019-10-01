package task;

public class DoAfter extends Task {
    protected String after;

    public DoAfter(String description, String after) {
        super(description);
        this.after = after;
    }

    @Override
    public String toString() {
        return "[DA]" + super.toString() + " (after: " + this.after + ")";
    }

    @Override
    public String toWriteFile() {
        int boolToInt = isDone ? 1 : 0;
        return "DA | " + boolToInt + " | " + this.description + " | " + this.after + "\n";
    }
}
