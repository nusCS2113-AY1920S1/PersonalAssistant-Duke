package task;

public class Recurring extends Task {
    public Recurring(String description, String date) {
        super(description);
        this.type = "R";
        this.date = date;
    }

    public Recurring(String description, String date, boolean state) {
        super(description);
        this.type = "R";
        this.date = date;
        this.isDone = state;
    }

    @Override
    public String toString() {
        return "[" + getType() + "]" + "[" + super.getStatusIcon() + "] " + super.getDescription()
                + " (every: " + super.getDate() + ")";
    }

    @Override
    public String toData() {
        return super.getType() + " | " + super.getStatus() + " | " + super.getDescription() + "| " + super.getDate();
    }
}
