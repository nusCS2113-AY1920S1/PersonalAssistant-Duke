import java.io.Serializable;

public abstract class Task implements Serializable {
    private boolean isDone;

    public Task() {
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public abstract boolean containsKeyword(String keyword);

    @Override
    public String toString() {
        return "[" + (this.isDone ? "✓" : "✘") + "]";
    }
}
