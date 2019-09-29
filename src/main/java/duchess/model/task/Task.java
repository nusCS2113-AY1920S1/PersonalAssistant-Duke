package duchess.model.task;

import duchess.logic.commands.exceptions.DukeException;
import duchess.model.TimeFrame;

import java.io.Serializable;
import java.util.List;

public abstract class Task implements Serializable, Comparable<Task> {
    private boolean isDone;

    public Task() {
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    @Override
    public String toString() {
        return "[" + (this.isDone ? "✓" : "✘") + "]";
    }

    @Override
    public int compareTo(Task that) {
        return this.getTimeFrame().compareTo(that.getTimeFrame());
    }

    public final boolean clashesWith(Task that) {
        return this.getTimeFrame().clashesWith(that.getTimeFrame());
    }

    public abstract TimeFrame getTimeFrame();

    public abstract void snooze() throws DukeException;

    public abstract List<Task> getReminders();

    public abstract boolean containsKeyword(String keyword);
}