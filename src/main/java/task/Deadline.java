package task;

public class Deadline extends Task {

    public Deadline(String description, String date) {
        super(description);
        this.type = "D";
        this.date = date;
    }

    public Deadline(String description, String date, boolean state) {
        super(description);
        this.type = "D";
        this.date = date;
        this.isDone = state;
    }

    @Override
    public String toString() {
        return "[" + getType() + "]" + "[" + super.getStatusIcon() + "] " + super.getDescription() + " (by: " + super.getDate() + ")";
    }

    @Override
    public String toData() {
        return super.getType() + " | " + super.getStatus() + " | " + super.getDescription() + "| " + super.getDate();
    }
}