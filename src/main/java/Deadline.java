public class Deadline implements ITask {
    private String description;
    private boolean isDone;
    private String initials;

    Deadline(String description) {
        this.description = description;
        this.isDone = false;
        this.initials = "D";
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
