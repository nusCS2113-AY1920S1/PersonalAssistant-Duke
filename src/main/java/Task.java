import java.io.Serializable;

public abstract class Task implements Serializable {
    private boolean isDone;

    public Task() {
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    @Override
    public String toString() {
        return "[" + (this.isDone ? "\u2713" : "\u2718") + "]";
    }
}
