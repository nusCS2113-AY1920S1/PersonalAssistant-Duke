package duke.modules;

import duke.exceptions.ModInvalidTimePeriodException;
import duke.util.DateTime;
import duke.util.TimeInterval;
import duke.util.TimePeriodSpanning;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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
        this.setPeriod(begin, this.getEnd().value());
    }

    public void setEnd(LocalDateTime end) throws ModInvalidTimePeriodException {
        this.setPeriod(this.getBegin().value(), end);
    }

    public LocalDateTime getTime() {
        return (this.getBegin() != null) ? this.getBegin().value() : this.getEnd().value();
    }

    public LocalDate getBeginDate() {
        return this.getBegin().value().toLocalDate();
    }

    @Override
    public LocalTime getBeginTime() {
        return this.getBegin().value().toLocalTime();
    }

    public LocalDate getEndDate() {
        return this.getEnd().value().toLocalDate();
    }

    @Override
    public LocalTime getEndTime() {
        return this.getEnd().value().toLocalTime();
    }

    @Override
    public DateTime<LocalDateTime> getBegin() {
        return this.period.getBegin();
    }

    @Override
    public DateTime<LocalDateTime> getEnd() {
        return this.period.getEnd();
    }

    @Override
    public List<DayOfWeek> getDaysOfWeek() {
        return this.period.getDaysOfWeek();
    }

    @Override
    public TimePeriodSpanning getPeriod() {
        return this.period;
    }

    @Override
    public TimeInterval getInterval() {
        return this.period.getInterval();
    }

    public boolean isClashing(LocalDateTime localDateTime) {
        return this.period.isClashing(localDateTime);
    }

    public boolean isClashing(LocalDateTime begin, LocalDateTime end) throws ModInvalidTimePeriodException {
        return this.period.isClashing(begin, end);
    }

    public boolean isClashing(TimePeriodSpanning timePeriodSpanning) {
        return this.period.isClashing(timePeriodSpanning);
    }

    public boolean isClashing(TaskWithSpanningPeriod other) {
        return this.period.isClashing(other.getPeriod());
    }
}
