public class ToDos implements ITask {
    private String description;
    private boolean isDone;
    private String initials;

    ToDos(String description) {
        this.description = description;
        this.isDone = false;
        this.initials = "T";
    }

    @Override
    public String getStatusIcon() {
        return (isDone ? "✓" : "✗"); //return tick or X symbols
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
