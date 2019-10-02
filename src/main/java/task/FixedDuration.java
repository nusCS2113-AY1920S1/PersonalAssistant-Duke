package task;

public class FixedDuration extends Task {
    public FixedDuration(String description, String date) {
        super(description);
        this.type = "F";
        this.date = date;
    }

    public FixedDuration(String description, String date, boolean state) {
        super(description);
        this.type = "F";
        this.date = date;
        this.isDone = state;
    }

    @Override
    public String toString() {
        return "[" + getType() + "]" + "[" + super.getStatusIcon() + "] " + super.getDescription()
                + "(needs: " + getDate() + ")";
    }

    @Override
    public String toData() {
        return super.getType() + " | " + super.getStatus() + " | " + super.getDescription() + "| " + getDate();
    }
}
