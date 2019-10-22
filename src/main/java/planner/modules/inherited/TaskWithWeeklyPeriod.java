package planner.modules.inherited;

import java.time.DayOfWeek;
import java.time.LocalTime;

import planner.util.periods.TimeInterval;
import planner.util.periods.TimePeriodWeekly;

public class TaskWithWeeklyPeriod extends TaskWithPeriod {

    TimePeriodWeekly period;

    public TaskWithWeeklyPeriod(String task, LocalTime begin, LocalTime end, DayOfWeek dayOfWeek) {
        super(task);
        this.period = new TimePeriodWeekly(begin, end, dayOfWeek);
    }

    public TaskWithWeeklyPeriod(String task, LocalTime begin, TimeInterval duration, DayOfWeek dayOfWeek) {
        super(task);
        this.period = new TimePeriodWeekly(begin, duration, dayOfWeek);
    }

    public TaskWithWeeklyPeriod(String task, TimePeriodWeekly period) {
        super(task);
        this.period = period;
    }

    public TaskWithWeeklyPeriod(String task, DayOfWeek dayOfWeek) {
        super(task);
        this.period = new TimePeriodWeekly(dayOfWeek);
    }

    public void setPeriod(LocalTime begin, LocalTime end) {
        this.period.setPeriod(begin, end);
    }

    public void setPeriod(LocalTime begin, TimeInterval duration) {
        this.period.setPeriod(begin, duration);
    }

    public void setBegin(LocalTime begin) {
        this.setPeriod(begin, (LocalTime) this.getEnd());
    }

    public void setEnd(LocalTime end) {
        this.setPeriod((LocalTime) this.getBegin(), end);
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.period.setDayOfWeek(dayOfWeek);
    }

    public LocalTime getTime() {
        return (LocalTime) ((this.getBegin() != null) ? this.getBegin() : this.getEnd());
    }

    public DayOfWeek getDayOfWeek() {
        return this.period.getDayOfWeek();
    }

    @Override
    public TimePeriodWeekly getPeriod() {
        return this.period;
    }

    public boolean isClashing(LocalTime localTime) {
        return this.period.isClashing(localTime);
    }

    public boolean isClashing(LocalTime begin, LocalTime end) {
        return this.period.isClashing(begin, end);
    }
}
