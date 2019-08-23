public class Event implements ITask {
    private String description;
    private boolean isDone;
    private String initials;

    Event(String description) {
        this.description = description;
        this.isDone = false;
        this.initials = "E";
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
        return this.description;
    }

    @Override
    public String getInitials() {
        return this.initials;
    }
}
