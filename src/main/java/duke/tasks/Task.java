package duke.tasks;

public abstract class Task {
    private String taskName;
    Boolean isDone;

    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "✓" : "✗"); //return tick or X symbols
    }

    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.getTaskName();
    }

    public String storeString() {
        return Integer.toString((isDone ? 1 : 0)) + " | " + this.getTaskName();
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void markDone() {
        this.isDone = true;
    }
}
