import java.io.Serializable;

public abstract class Task implements Serializable {
    private boolean isDone;

    public Task() {
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    abstract public boolean containsKeyword(String keyword);

    @Override
    public String toString() {
        return "[" + (this.isDone ? "\u2713" : "\u2718") + "]";
    }
}
