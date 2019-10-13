package duke.modules;

import duke.util.DateTime;
import duke.util.TimeInterval;
import duke.util.TimePeriodWeekly;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

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
        this.setPeriod(begin, this.getEnd());
    }

    public void setEnd(LocalTime end) {
        this.setPeriod(this.getBegin(), end);
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.period.setDayOfWeek(dayOfWeek);
    }

    public LocalTime getTime() {
        return (this.getBegin() != null) ? this.getBegin() : this.getEnd();
    }

    public DayOfWeek getDayOfWeek() {
        return this.period.getDayOfWeek();
    }

    @Override
    public List<DayOfWeek> getDaysOfWeek() {
        return this.period.getDaysOfWeek();
    }

    @Override
    public LocalTime getBeginTime() {
        return this.period.getBegin();
    }

    @Override
    public LocalTime getEndTime() {
        return this.period.getEnd();
    }

    @Override
    public LocalTime getBegin() {
        return this.period.getBegin();
    }

    @Override
    public LocalTime getEnd() {
        return this.period.getEnd();
    }

    @Override
    public TimePeriodWeekly getPeriod() {
        return this.period;
    }

    @Override
    public TimeInterval getInterval() {
        return this.period.getInterval();
    }

    public boolean isClashing(LocalTime localTime) {
        return this.period.isClashing(localTime);
    }

    public boolean isClashing(LocalTime begin, LocalTime end) {
        return this.period.isClashing(begin, end);
    }

    public boolean isClashing(TimePeriodWeekly timePeriodWeekly) {
        return this.period.isClashing(timePeriodWeekly);
    }

    public boolean isClashing(TaskWithWeeklyPeriod other) {
        return this.period.isClashing(other.getPeriod());
    }
}
