package duke.modules;

import duke.exceptions.ModInvalidTimePeriodException;
import duke.util.TimeInterval;
import duke.util.TimePeriod;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TaskWithPeriod extends Task {

    TimePeriod period;

    public TaskWithPeriod(String task, LocalDateTime begin, LocalDateTime end) throws ModInvalidTimePeriodException {
        super(task);
        this.period = new TimePeriod(begin, end);
    }

    public TaskWithPeriod(String task, LocalDateTime begin, TimeInterval duration) throws ModInvalidTimePeriodException {
        super(task);
        this.period = new TimePeriod(begin, duration);
    }

    public TaskWithPeriod(String task, TimePeriod period) {
        super(task);
        this.period = period;
    }

    public TaskWithPeriod(String task) throws ModInvalidTimePeriodException {
        super(task);
        this.period = new TimePeriod();
    }

    public TimePeriod getPeriod() {
        return this.period;
    }

    public TimeInterval getInterval() {
        return this.period.getInterval();
    }

    public void setPeriod(LocalDateTime begin, LocalDateTime end) throws ModInvalidTimePeriodException {
        this.period.setPeriod(begin, end);
    }

    public void setPeriod(LocalDateTime begin, TimeInterval duration) throws ModInvalidTimePeriodException {
        this.period.setPeriod(begin, duration);
    }

    public void setBegin(LocalDateTime begin) throws ModInvalidTimePeriodException {
        this.setPeriod(begin, this.getEnd());
    }

    public void setEnd(LocalDateTime end) throws ModInvalidTimePeriodException {
        this.setPeriod(this.getBegin(), end);
    }

    @Override
    public LocalDateTime getTime() {
        return (this.getBegin() != null) ? this.getBegin() : this.getEnd();
    }

    public LocalDateTime getBegin() {
        return this.period.getBegin();
    }

    public LocalDateTime getEnd() {
        return this.period.getEnd();
    }

    public LocalDate getBeginDate() {
        return this.getBegin().toLocalDate();
    }

    public LocalTime getBeginTime() {
        return this.getBegin().toLocalTime();
    }

    public LocalDate getEndDate() {
        return this.getEnd().toLocalDate();
    }

    public LocalTime getEndTime() {
        return this.getEnd().toLocalTime();
    }

    public boolean isClashing(LocalDateTime localDateTime) {
        return this.period.isClashing(localDateTime);
    }

    public boolean isClashing(LocalDateTime begin, LocalDateTime end) {
        return this.period.isClashing(begin, end);
    }

    public boolean isClashing(TimePeriod timePeriod) {
        return this.period.isClashing(timePeriod);
    }

    public boolean isClashing(TaskWithPeriod other) {
        return this.period.isClashing(other.getPeriod());
    }
}
