class ToDos implements ITask {
    private String description;
    private boolean isDone;
    private String initials;

    ToDos(String description) {
        this.description = description;
        this.isDone = false;
        this.initials = "T";
    }

    public String getStatusIcon() {
        return (isDone ? "✓" : "✗"); //return tick or X symbols
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String getInitials() {
        return this.initials;
    }
}
