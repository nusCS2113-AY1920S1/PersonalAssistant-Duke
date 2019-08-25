public abstract class Task {
    private String name;
    private boolean isDone;

    public Task() {
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public String toString() {
        return "[" + (this.isDone ? "\u2713" : "\u2718") + "]";
    }
}
