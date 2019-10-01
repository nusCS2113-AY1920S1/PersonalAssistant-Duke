package duchess.model.task;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import duchess.logic.commands.exceptions.DukeException;
import duchess.model.TimeFrame;

import java.util.List;

public abstract class Task implements Comparable<Task> {
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

    @JsonGetter("isDone")
    public boolean isDone() {
        return isDone;
    }

    @JsonSetter("isDone")
    public void setDone(boolean done) {
        isDone = done;
    }
}