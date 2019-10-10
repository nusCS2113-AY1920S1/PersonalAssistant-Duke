package duke.util;

import duke.exceptions.DukeInvalidTimePeriodException;

import java.time.LocalDateTime;

public class TimePeriod {

    private LocalDateTime begin;
    private LocalDateTime end;

    /**
     * Constructor for TimePeriod check.
     * @param begin Start date.
     * @param end End date.
     * @throws DukeInvalidTimePeriodException thrown when date period is invalid.
     */
    public TimePeriod(LocalDateTime begin, LocalDateTime end) throws DukeInvalidTimePeriodException {
        if (end.isBefore(begin)) {
            throw new DukeInvalidTimePeriodException("End before begin!");
        }
        this.begin = begin;
        this.end = end;
    }

    public TimePeriod(LocalDateTime begin) throws DukeInvalidTimePeriodException {
        this(begin, begin);
    }

    /**
     * Checker function for clashing time periods.
     * @param localDateTime Given LocalDateTime.
     * @param strictBegin Starting date boolean test result.
     * @param strictEnd Ending data boolean test result.
     * @return Boolean result if the set period is a valid period.
     */
    public boolean isClashing(LocalDateTime localDateTime, boolean strictBegin, boolean strictEnd) {
        return localDateTime.isAfter(this.begin) && localDateTime.isBefore(this.end)
                || strictBegin && localDateTime.isEqual(this.begin)
                || strictEnd && localDateTime.isEqual(this.end);
    }

    public boolean isClashing(LocalDateTime localDateTime) {
        return this.isClashing(localDateTime, false, false);
    }

    public boolean isClashing(TimePeriod other) {
        return other.isClashing(this.begin) || other.isClashing(this.end);
    }

    public LocalDateTime getBegin() {
        return this.begin;
    }

    public LocalDateTime getEnd() {
        return this.end;
    }

    public void setBegin(LocalDateTime begin) {
        this.begin = begin;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public void setPeriod(LocalDateTime begin, LocalDateTime end) {
        this.begin = begin;
        this.end = end;
    }

    public boolean isExpired() {
        LocalDateTime now = LocalDateTime.now();
        return this.end.isBefore(now) || this.end.isEqual(now);
    }

    public TimeInterval getInterval() {
        return TimeInterval.between(this.begin, this.end);
    }
}
