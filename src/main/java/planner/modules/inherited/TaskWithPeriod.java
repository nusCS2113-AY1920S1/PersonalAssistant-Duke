package planner.modules.inherited;

import planner.util.periods.TimeInterval;
import planner.util.periods.TimePeriod;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.List;

public abstract class TaskWithPeriod extends Task {

    public TaskWithPeriod(String task) {
        super(task);
    }

    public TimeInterval getInterval() {
        return new TimeInterval(this.getPeriod());
    }

    public LocalTime getBeginTime() {
        return this.getPeriod().getBeginTime();
    }

    public LocalTime getEndTime() {
        return this.getPeriod().getEndTime();
    }

    public Temporal getBegin() {
        return this.getPeriod().getBegin();
    }

    public Temporal getEnd() {
        return this.getPeriod().getEnd();
    }

    public abstract TimePeriod getPeriod();

    public List<DayOfWeek> getDaysOfWeek() {
        return this.getPeriod().getDaysOfWeek();
    }

    public <T extends TemporalAccessor> boolean isClashing(T other) {
        return this.getPeriod().isClashing(other);
    }

    public <E extends TaskWithPeriod> boolean isClashing(E other) {
        return this.isClashing(other.getPeriod());
    }

    public <E extends TimePeriod> boolean isClashing(E other) {
        return this.getPeriod().isClashing(other);
    }
}
