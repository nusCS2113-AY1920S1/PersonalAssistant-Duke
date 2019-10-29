package duchess.model.task;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import duchess.exceptions.DuchessException;
import duchess.model.Grade;
import duchess.model.Module;
import duchess.model.TimeFrame;

import java.util.Optional;

public abstract class Task implements Comparable<Task> {
    private boolean isDone;
    private Optional<Module> module;
    private Optional<Grade> grade;
    protected String description;

    /**
     * Creates a Task.
     */
    public Task() {
        this.isDone = false;
        this.module = Optional.empty();
        this.grade = Optional.empty();
    }

    public boolean clashesWith(Task that) {
        return this.getTimeFrame().clashesWith(that.getTimeFrame());
    }

    public Optional<Module> getModule() {
        return this.module;
    }

    public Optional<Grade> getGrade() {
        return this.grade;
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

    @JsonSetter("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonSetter("module")
    public void setModule(Module module) {
        this.module = Optional.ofNullable(module);
    }

    @JsonGetter("module")
    public Module getRawtModule() {
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

    @JsonSetter("grade")
    public void setGrade(Grade grade) {
        this.grade = Optional.ofNullable(grade);
    }

    @JsonGetter("grade")
    public Grade getRawtGrade() {
        return this.grade.orElse(null);
    }

    public abstract TimeFrame getTimeFrame();

    public abstract void snooze() throws DuchessException;

    public abstract Optional<Task> getReminder();

}