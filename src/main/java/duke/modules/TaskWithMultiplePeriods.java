package duke.modules;

import duke.exceptions.ModInvalidIndexException;
import duke.exceptions.ModInvalidTimePeriodException;
import duke.util.TimeInterval;
import duke.util.TimePeriod;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class TaskWithMultiplePeriods<E extends TimePeriod> extends Task {

    List<E> periods;

    public TaskWithMultiplePeriods(String task) {
        super(task);
    }

    public TimeInterval getInterval(int index) throws ModInvalidIndexException {
        return new TimeInterval(this.getPeriod(index));
    }

    public LocalTime getBeginTime(int index) throws ModInvalidIndexException {
        return this.getPeriod(index).getBeginTime();
    }

    public LocalTime getEndTime(int index) throws ModInvalidIndexException {
        return this.getPeriod(index).getEndTime();
    }

    public Temporal getBegin(int index) throws ModInvalidIndexException {
        return this.getPeriod(index).getBegin();
    }

    public Temporal getEnd(int index) throws ModInvalidIndexException {
        return this.getPeriod(index).getEnd();
    }

    public E getPeriod(int index) throws ModInvalidIndexException {
        this.checkIndex(index);
        return this.getPeriods().get(index);
    }

    /**
     * Set period for a task.
     * @param index task index
     * @param period new period
     * @throws ModInvalidIndexException when input index is invalid
     */
    public void setPeriod(int index, E period) throws ModInvalidIndexException {
        try {
            this.getPeriods().set(index, period);
        } catch (IndexOutOfBoundsException ex) {
            throw new ModInvalidIndexException();
        }
    }

    void checkIndex(int index) throws ModInvalidIndexException {
        if (index < 0 || index >= this.getPeriods().size()) {
            throw new ModInvalidIndexException();
        }
    }

    public List<E> getPeriods() {
        return this.periods;
    }

    public void setPeriods(List<E> periods) {
        this.periods = periods;
    }

    /**
     * Get all days of week on which this task takes place.
     * @return a list of days of week on which this task takes place
     */
    public List<DayOfWeek> getDaysOfWeek() {
        Set<DayOfWeek> result = new HashSet<>();
        for (E timePeriod: this.getPeriods()) {
            result.addAll(timePeriod.getDaysOfWeek());
        }
        return new ArrayList<>(result);
    }

    public <T extends TaskWithPeriod> boolean isClashing(T other) {
        return this.isClashing(other.getPeriod());
    }

    /**
     * Check for clashing with given TimePeriod.
     * @param other given TimePeriod
     * @return true if clashing else false
     */
    public <T extends TimePeriod> boolean isClashing(T other) {
        for (E timePeriod: this.getPeriods()) {
            if (timePeriod.isClashing(other)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check for clashing with given List of TimePeriod.
     * @param other given List of TimePeriod
     * @return true if clashing else false
     */
    public boolean isClashing(List<? extends TimePeriod> other) {
        for (E timePeriod: this.getPeriods()) {
            for (TimePeriod otherTimePeriod: other) {
                if (timePeriod.isClashing(otherTimePeriod)) {
                    return true;
                }
            }
        }
        return false;
    }

    public <T extends TaskWithMultiplePeriods> boolean isClashing(T other) {
        return this.isClashing(other.getPeriods());
    }

    /**
     * Check for clashing with given TemporalAccessor.
     * @param other given TemporalAccessor
     * @return true if clashing else false
     */
    public <T extends TemporalAccessor> boolean isClashing(T other) {
        for (E timePeriod: this.getPeriods()) {
            if (timePeriod.isClashing(other)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check for clashing with time between two given TemporalAccessors.
     * @param begin begin of the duration
     * @param end begin of the duration
     * @return true if clashing else false
     */
    public <T extends TemporalAccessor> boolean isClashing(T begin, T end) throws ModInvalidTimePeriodException {
        for (E timePeriod: this.getPeriods()) {
            if (timePeriod.isClashing(begin, end)) {
                return true;
            }
        }
        return false;
    }

    void initPeriodList() {
        if (this.periods == null) {
            this.periods = new ArrayList<>();
        }
    }

    /**
     * Add a period.
     * @param timePeriod new period
     */
    public void addPeriod(E timePeriod) {
        this.initPeriodList();
        this.getPeriods().add(timePeriod);
    }

    public Temporal getTime(int index) throws ModInvalidIndexException {
        return (this.getBegin(index) != null) ? this.getBegin(index) : this.getEnd(index);
    }
}
