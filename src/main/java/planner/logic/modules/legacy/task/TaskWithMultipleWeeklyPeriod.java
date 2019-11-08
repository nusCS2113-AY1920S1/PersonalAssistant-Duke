//@@author LongLeCE

package planner.logic.modules.legacy.task;

import planner.logic.exceptions.legacy.ModInvalidIndexException;
import planner.logic.exceptions.legacy.ModInvalidTimeException;
import planner.util.legacy.periods.TimeInterval;
import planner.util.legacy.periods.TimePeriod;
import planner.util.legacy.periods.TimePeriodWeekly;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
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

    public TaskWithMultipleWeeklyPeriod(String task) {
        super(task);
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

    //@@author e0313687
    public boolean happensOnThisDayOfWeek(DayOfWeek dayOfWeek) {
        return this.getDaysOfWeek().contains(dayOfWeek);
    }

    /**
     * Returns a sorted list of this task's time period of the given day.
     *
     * @param dayOfWeek The day of the week
     * @return a sorted list of this task's time period of the given day
     */
    public List<TimePeriodWeekly> getTimePeriodOfTheDay(DayOfWeek dayOfWeek) {
        List<TimePeriodWeekly> timePeriods = new ArrayList<>();
        List<TimePeriodWeekly> list = this.getPeriods();
        for (TimePeriodWeekly item : list) {
            //loop through all the periods, see which period falls under that day
            if (dayOfWeek.equals(item.getDayOfWeek())) {
                timePeriods.add(item);
            }
        }
        timePeriods.sort(Comparator.comparing(TimePeriodWeekly::getBegin));
        //with the current infrastructure I can only loop through, advise to write a helper function
        return timePeriods;
    }

    /**
     * Returns a string describing task on the give dayOfWeek.
     */
    public String onWeekDayToString(DayOfWeek day) {
        List<TimePeriodWeekly> list = this.getTimePeriodOfTheDay(day);
        return list.toString();
    }

    public static DayOfWeek getDayOfWeek(String dayOfWeek) throws ModInvalidTimeException {
        try {
            return DayOfWeek.valueOf(dayOfWeek.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ModInvalidTimeException();
        }
    }
}
