package duke.util;

import duke.exceptions.ModInvalidTimePeriodException;

import java.time.LocalDateTime;

public class TimePeriod {

    private LocalDateTime begin;
    private LocalDateTime end;

    /**
     * Constructor for TimePeriod check.
     * @param begin Start date.
     * @param end End date.
     * @throws ModInvalidTimePeriodException thrown when date period is invalid.
     */
    public TimePeriod(LocalDateTime begin, LocalDateTime end) throws ModInvalidTimePeriodException {
        this.setPeriod(begin, end);
    }

    /**
     * Constructor for TimePeriod check.
     * @param begin Start date.
     * @param isInstantEnd Ends immediately or not.
     * @throws ModInvalidTimePeriodException thrown when date period is invalid.
     */
    public TimePeriod(LocalDateTime begin, boolean isInstantEnd) throws ModInvalidTimePeriodException {
        this(begin, null);
        if (isInstantEnd) {
            this.setEnd(this.getBegin());
        }
    }

    public TimePeriod(LocalDateTime begin) throws ModInvalidTimePeriodException {
        this(begin, true);
    }

    public TimePeriod() throws ModInvalidTimePeriodException {
        this(null, null);
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

    public boolean isClashing(LocalDateTime begin, LocalDateTime end) {
        return this.isClashing(begin) || this.isClashing(end);
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

    /**
     * Set period for this object.
     * @param begin Start date.
     * @param end End date.
     * @throws ModInvalidTimePeriodException thrown when date period is invalid.
     */
    public void setPeriod(LocalDateTime begin, LocalDateTime end) throws ModInvalidTimePeriodException {
        if (end != null && begin != null && end.isBefore(begin)) {
            throw new ModInvalidTimePeriodException("End before begin!");
        }
        this.begin = begin;
        this.end = end;
    }

    public void setPeriod(LocalDateTime begin, TimeInterval duration) throws ModInvalidTimePeriodException {
        this.setPeriod(begin, begin.plus(duration));
    }

    public boolean isExpired() {
        LocalDateTime now = LocalDateTime.now();
        return this.end != null && (this.end.isBefore(now) || this.end.isEqual(now));
    }

    public TimeInterval getInterval() {
        return TimeInterval.between(this.begin, this.end);
    }

    /**
     * Make period expire immediately.
     */
    public void endsNow() {
        if (!this.isExpired()) {
            this.setEnd(LocalDateTime.now());
        }
    }
}
