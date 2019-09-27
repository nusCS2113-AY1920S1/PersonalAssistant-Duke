package duchess.model.task;

import duchess.logic.commands.exceptions.DukeException;
import duchess.model.Schedule;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    public abstract Schedule isWithinTimeFrame(Date startDate, Date endDate);

    public abstract void snooze() throws DukeException;

    public abstract List<Task> getReminders();

    public abstract List<Task> getClashables();

    public abstract boolean clashesWith(Task task);
}
