package duchess.model.task;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import duchess.exceptions.DuchessException;
import duchess.model.Module;
import duchess.model.TimeFrame;

import java.util.Optional;

public abstract class Task implements Comparable<Task> {
    private boolean isDone;
    private Optional<Module> module;
    protected String description;

    public Task() {
        this.isDone = false;
        this.module = Optional.empty();
    }

    public void markAsDone() {
        this.isDone = true;
    }

    @Override
    public String toString() {
        return
                "[" + (this.isDone ? "✓" : "✘") + "]"
                        + module.map(m -> "[" + m.getCode() + "]").orElse("");
    }

    @Override
    public int compareTo(Task that) {
        return this.getTimeFrame().compareTo(that.getTimeFrame());
    }

    public final boolean clashesWith(Task that) {
        return this.getTimeFrame().clashesWith(that.getTimeFrame());
    }

    @JsonSetter("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonSetter("module")
    public void setModule(Module module) {
        this.module = Optional.ofNullable(module);
    }

    @JsonGetter("module")
    public Module getModule() {
        return this.module.orElse(null);
    }

    @JsonGetter("done")
    public boolean isDone() {
        return isDone;
    }

    @JsonGetter("description")
    public String getDescription() {
        return description;
    }

    @JsonSetter("done")
    public void setDone(boolean done) {
        isDone = done;
    }

    public boolean containsKeyword(String keyword) {
        return this.description.contains(keyword);
    }

    public abstract TimeFrame getTimeFrame();

    public abstract void snooze() throws DuchessException;

    public abstract Optional<Task> getReminder();
}