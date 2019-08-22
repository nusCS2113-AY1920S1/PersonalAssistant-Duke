class Task {
    private String description;
    private boolean isDone;

    Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    String getStatusIcon() {
        return (isDone ? "✓" : "✗"); //return tick or X symbols
    }

    void markAsDone() {
        isDone = true;
    }

    String getDescription() {
        return description;
    }
}
