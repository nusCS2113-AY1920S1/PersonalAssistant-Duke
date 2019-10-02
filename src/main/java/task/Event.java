package task;

/**
 * Represents the object that manages event tasks.
 * Inherits from Task class.
 */
public class Event extends Task {

    public Event(String description, String date) {
        super(description);
        this.type = "E";
        this.date = date;
    }

    public Event(String description, String date, boolean state) {
        super(description);
        this.type = "E";
        this.date = date;
        this.isDone = state;
    }

    @Override
    public String toString() {
        return "[" + getType() + "]" + "[" + super.getStatusIcon() + "] " + super.getDescription()
                + "(at: " + getDate() + ")";
    }

    @Override
    public String toData() {
        return super.getType() + " | " + super.getStatus() + " | " + super.getDescription() + "| " + getDate();
    }
}