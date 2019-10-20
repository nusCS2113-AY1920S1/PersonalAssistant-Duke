package planner.modules;

import planner.exceptions.ModInvalidTimePeriodException;
import planner.util.TimeInterval;
import planner.util.TimePeriodSpanning;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskWithSpanningPeriod extends TaskWithPeriod {

    TimePeriodSpanning period;

    public TaskWithSpanningPeriod(String task, LocalDateTime begin, LocalDateTime end)
            throws ModInvalidTimePeriodException {
        super(task);
        this.period = new TimePeriodSpanning(begin, end);
    }

    public TaskWithSpanningPeriod(String task, LocalDateTime begin, TimeInterval duration)
            throws ModInvalidTimePeriodException {
        super(task);
        this.period = new TimePeriodSpanning(begin, duration);
    }

    public TaskWithSpanningPeriod(String task, TimePeriodSpanning period) {
        super(task);
        this.period = period;
    }

    public TaskWithSpanningPeriod(String task) throws ModInvalidTimePeriodException {
        super(task);
        this.period = new TimePeriodSpanning();
    }

    public void setPeriod(LocalDateTime begin, LocalDateTime end) throws ModInvalidTimePeriodException {
        this.period.setPeriod(begin, end);
    }

    public void setPeriod(LocalDateTime begin, TimeInterval duration) throws ModInvalidTimePeriodException {
        this.period.setPeriod(begin, duration);
    }

    public void setBegin(LocalDateTime begin) throws ModInvalidTimePeriodException {
        this.setPeriod(begin, (LocalDateTime) this.getEnd());
    }

    public void setEnd(LocalDateTime end) throws ModInvalidTimePeriodException {
        this.setPeriod((LocalDateTime) this.getBegin(), end);
    }

    public LocalDateTime getTime() {
        return (LocalDateTime) ((this.getBegin() != null) ? this.getBegin() : this.getEnd());
    }

    public LocalDate getBeginDate() {
        return ((LocalDateTime) this.getBegin()).toLocalDate();
    }

    public LocalDate getEndDate() {
        return ((LocalDateTime) this.getEnd()).toLocalDate();
    }

    @Override
    public TimePeriodSpanning getPeriod() {
        return this.period;
    }

    public boolean isClashing(LocalDateTime localDateTime) {
        return this.period.isClashing(localDateTime);
    }

    public boolean isClashing(LocalDateTime begin, LocalDateTime end) throws ModInvalidTimePeriodException {
        return this.period.isClashing(begin, end);
    }
}
