public class Deadline implements ITask {
    private String description;
    private boolean isDone;
    private String initials;
    private String dueDate;

    Deadline(String description, String dueDate) {
        this.description = description;
        this.isDone = false;
        this.initials = "D";
        this.dueDate = dueDate;
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
        return this.description + " (by: " + this.dueDate + ")";
    }

    @Override
    public String getInitials() {
        return this.initials;
    }
}
