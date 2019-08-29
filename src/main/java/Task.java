public class Task {
    protected String description;
    protected boolean isDone;
    private final String ticks = "\u2713";
    private final String cross = "\u2718";

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? ticks : cross);
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public String toString() {
        return ("[" + getStatusIcon() + "] "
                + description);
    }

    public String fileOutFormat() {
        return ("|" + isDone + "|" + description);
    }
}