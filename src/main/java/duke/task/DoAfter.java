package duke.task;

public class DoAfter extends Tasks {
    String after;

    public DoAfter(String description, String type, String after) {
        super(description, type);
        this.after = after;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String toMessage() {
        return description
            + "(after: " + after + ")";
    }
}
