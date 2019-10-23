package duke.task;

public class Recurring extends Tasks {
    private String recur;

    public Recurring(String description, String type, String recur) {
        super(description, type);
        this.recur = recur;
    }

    public String getRecur() {
        return recur;
    }

    public String toMessage() {
        return description
                + " (frequency: " + recur + ")";
    }
}
