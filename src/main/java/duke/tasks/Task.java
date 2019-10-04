package duke.tasks;

public class Task {
    public String description;
    public boolean isDone;
    private final String ticks = "Y";
    private final String cross = "N";

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

    public boolean checkKeyword(String str) {
        return (this.description.contains(str));
    }

    public String toString() {
        return ("[" + getStatusIcon() + "] "
                + description);
    }

    public String fileOutFormat() {
        return ("|" + isDone + "|" + description);
    }
}