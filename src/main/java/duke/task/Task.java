package duke.task;

import java.io.Serializable;
import java.util.Date;

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

    public abstract boolean isWithinTimeFrame(Date start, Date end);
}
