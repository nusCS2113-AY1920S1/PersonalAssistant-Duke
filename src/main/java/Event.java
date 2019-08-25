public class Event implements ITask {
    private String description;
    private boolean isDone;
    private String initials;
    private String timing;

    Event(String description, String timing) {
        this.description = description;
        this.isDone = false;
        this.initials = "E";
        this.timing = timing;
    }

    @Override
    public String getStatusIcon() {
        return (isDone ? "✓" : "✗");
    }

    @Override
    public void markAsDone() {
        this.isDone = true;
    }

    @Override
    public String getDescription() {
        return this.description + " (at:" + this.timing + ")";
    }

    @Override
    public String getInitials() {
        return this.initials;
    }
}
