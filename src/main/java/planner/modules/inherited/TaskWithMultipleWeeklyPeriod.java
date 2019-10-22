package planner.modules.inherited;

import planner.exceptions.original.ModInvalidIndexException;
import planner.util.periods.TimeInterval;
import planner.util.periods.TimePeriodWeekly;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public class TaskWithMultipleWeeklyPeriod extends TaskWithMultiplePeriods<TimePeriodWeekly> {

    public TaskWithMultipleWeeklyPeriod(String task, LocalTime begin, LocalTime end, DayOfWeek dayOfWeek) {
        super(task);
        this.addPeriod(begin, end, dayOfWeek);
    }

    public TaskWithMultipleWeeklyPeriod(String task, LocalTime begin, TimeInterval duration, DayOfWeek dayOfWeek) {
        super(task);
        this.addPeriod(begin, duration, dayOfWeek);
    }

    public TaskWithMultipleWeeklyPeriod(String task, TimePeriodWeekly period) {
        super(task);
        this.addPeriod(period);
    }

    public TaskWithMultipleWeeklyPeriod(String task, DayOfWeek dayOfWeek) {
        super(task);
        this.addPeriod(dayOfWeek);
    }

    public TaskWithMultipleWeeklyPeriod(String task, List<TimePeriodWeekly> periods) {
        super(task);
        this.setPeriods(periods);
    }

    public void addPeriod(LocalTime begin, LocalTime end, DayOfWeek dayOfWeek) {
        this.addPeriod(new TimePeriodWeekly(begin, end, dayOfWeek));
    }

    public void addPeriod(LocalTime begin, TimeInterval duration, DayOfWeek dayOfWeek) {
        this.addPeriod(new TimePeriodWeekly(begin, duration, dayOfWeek));
    }

    public void addPeriod(DayOfWeek dayOfWeek) {
        this.addPeriod(new TimePeriodWeekly(dayOfWeek));
    }

    public void removePeriod(int index) {
        this.periods.remove(index);
    }

    public void setPeriod(int index, LocalTime begin, LocalTime end) throws ModInvalidIndexException {
        this.getPeriod(index).setPeriod(begin, end);
    }

    public void setPeriod(int index, LocalTime begin, TimeInterval duration) throws ModInvalidIndexException {
        this.getPeriod(index).setPeriod(begin, duration);
    }

    public void setBegin(int index, LocalTime begin) throws ModInvalidIndexException {
        this.setPeriod(index, begin, (LocalTime) this.getEnd(index));
    }

    public void setEnd(int index, LocalTime end) throws ModInvalidIndexException {
        this.setPeriod(index, (LocalTime) this.getBegin(index), end);
    }

    public void setDayOfWeek(int index, DayOfWeek dayOfWeek) throws ModInvalidIndexException {
        this.getPeriod(index).setDayOfWeek(dayOfWeek);
    }

    public DayOfWeek getDayOfWeek(int index) throws ModInvalidIndexException {
        return this.getPeriod(index).getDayOfWeek();
    }
}
