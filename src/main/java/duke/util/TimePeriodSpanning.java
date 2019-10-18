package duke.util;

import duke.exceptions.ModInvalidTimePeriodException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimePeriodSpanning implements TimePeriod {

    private LocalDateTime begin;
    private LocalDateTime end;

    /**
     * Constructor for TimePeriod check.
     * @param begin Start date.
     * @param end End date.
     * @throws ModInvalidTimePeriodException thrown when date period is invalid.
     */
    public TimePeriodSpanning(LocalDateTime begin, LocalDateTime end) throws ModInvalidTimePeriodException {
        this.setPeriod(begin, end);
    }

    public TimePeriodSpanning(LocalDate begin, LocalDate end) throws ModInvalidTimePeriodException {
        this.setPeriod(LocalDateTime.of(begin, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MIN));
    }

    public TimePeriodSpanning(LocalTime begin, LocalTime end) throws ModInvalidTimePeriodException {
        this.setPeriod(LocalDateTime.of(LocalDate.now(), begin), LocalDateTime.of(LocalDate.now(), end));
    }

    /**
     * Constructor for TimePeriod check.
     * @param begin Start date.
     * @param duration Duration of the period.
     * @throws ModInvalidTimePeriodException thrown when date period is invalid.
     */
    public TimePeriodSpanning(LocalDateTime begin, TimeInterval duration) throws ModInvalidTimePeriodException {
        this.setPeriod(begin, duration);
    }

    public TimePeriodSpanning(LocalDate begin, TimeInterval duration) throws ModInvalidTimePeriodException {
        this.setPeriod(LocalDateTime.of(begin, LocalTime.MIN), duration);
    }

    public TimePeriodSpanning(LocalTime begin, TimeInterval duration) throws ModInvalidTimePeriodException {
        this.setPeriod(LocalDateTime.of(LocalDate.now(), begin), duration);
    }

    /**
     * Constructor for TimePeriod check.
     * @param begin Start date.
     * @param isInstantEnd Ends immediately or not.
     * @throws ModInvalidTimePeriodException thrown when date period is invalid.
     */
    public TimePeriodSpanning(LocalDateTime begin, boolean isInstantEnd) throws ModInvalidTimePeriodException {
        this(begin, (LocalDateTime) null);
        if (isInstantEnd) {
            this.setEnd(this.begin);
        }
    }

    public TimePeriodSpanning(LocalDate begin, boolean isInstantEnd) throws ModInvalidTimePeriodException {
        this(LocalDateTime.of(begin, LocalTime.MIN), isInstantEnd);
    }

    public TimePeriodSpanning(LocalTime begin, boolean isInstantEnd) throws ModInvalidTimePeriodException {
        this(LocalDateTime.of(LocalDate.now(), begin), isInstantEnd);
    }

    public TimePeriodSpanning(LocalDateTime begin) throws ModInvalidTimePeriodException {
        this(begin, true);
    }

    public TimePeriodSpanning(LocalDate begin) throws ModInvalidTimePeriodException {
        this(begin, true);
    }

    public TimePeriodSpanning(LocalTime begin) throws ModInvalidTimePeriodException {
        this(begin, true);
    }

    public TimePeriodSpanning() throws ModInvalidTimePeriodException {
        this(null, (LocalDateTime) null);
    }

    /**
     * Checker function for clashing time periods.
     * @param localTime Given LocalTime.
     * @param strictBegin Strict clashing for begin or not (begin matches ends).
     * @param strictEnd Strict clashing for end or not (begin matches ends).
     * @return Boolean result if the period clash.
     */
    public boolean isClashing(LocalTime localTime, boolean strictBegin, boolean strictEnd) {
        LocalTime begin = this.begin.toLocalTime();
        LocalTime end = this.end.toLocalTime();
        return localTime != null
                && (localTime.isAfter(begin)
                && localTime.isBefore(end)
                || strictBegin && localTime.equals(begin)
                || strictEnd && localTime.equals(end));
    }

    /**
     * Checker function for clashing time periods.
     * @param localDateTime Given LocalDateTime.
     * @param strictBegin Strict clashing for begin or not (begin matches ends).
     * @param strictEnd Strict clashing for end or not (begin matches ends).
     * @return Boolean result if the period clash.
     */
    public boolean isClashing(LocalDateTime localDateTime, boolean strictBegin, boolean strictEnd) {
        return localDateTime != null
                && (localDateTime.isAfter(this.begin)
                && localDateTime.isBefore(this.end)
                || strictBegin && localDateTime.equals(this.begin)
                || strictEnd && localDateTime.equals(this.end));
    }

    public boolean isClashing(LocalDateTime localDateTime) {
        return this.isClashing(localDateTime, false, false);
    }

    public boolean isClashing(LocalTime localTime) {
        return this.isClashing(localTime, false, false);
    }

    public boolean isClashing(LocalDateTime begin, LocalDateTime end) throws ModInvalidTimePeriodException {
        return this.isClashing(new TimePeriodSpanning(begin, end));
    }

    public boolean isClashing(LocalDate begin, LocalDate end) throws ModInvalidTimePeriodException {
        return this.isClashing(new TimePeriodSpanning(begin, end));
    }

    public boolean isClashing(LocalTime begin, LocalTime end) throws ModInvalidTimePeriodException {
        return this.isClashing(new TimePeriodSpanning(begin, end));
    }

    @Override
    public <E extends TemporalAccessor> boolean isClashing(E other) {
        return this.isClashing(LocalDateTime.from(other));
    }

    @Override
    public <E extends TemporalAccessor> boolean isClashing(E begin, E end) throws ModInvalidTimePeriodException {
        return this.isClashing(LocalDateTime.from(begin), LocalDateTime.from(end));
    }

    /**
     * Check whether time period clashes with another.
     * @param other the other time period
     * @return true if clashes, false otherwise
     */
    public boolean isClashing(TimePeriodSpanning other) {
        return other != null
                && (this.begin == other.begin && this.end == other.end
                    || other.isClashing(this.begin)
                    || other.isClashing(this.end)
                    || this.isClashing(other.begin)
                    || this.isClashing(other.end));
    }

    // TODO: Combine the isClashing of TimePeriods
    @Override
    public boolean isClashing(TimePeriod other) {
        if (other instanceof TimePeriodSpanning) {
            return this.isClashing((TimePeriodSpanning) other);
        } else if (other instanceof  TimePeriodWeekly) {
            return this.isClashing((TimePeriodWeekly) other);
        }
        return false;
    }

    public boolean isClashing(TimePeriodWeekly other) {
        return other != null && other.isClashing(this);
    }

    public void setBegin(LocalDateTime begin) throws ModInvalidTimePeriodException {
        this.setPeriod(begin, this.end);
    }

    public void setEnd(LocalDateTime end) throws ModInvalidTimePeriodException {
        this.setPeriod(this.begin, end);
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

    /**
     * Make period expire immediately.
     */
    public void endsNow() throws ModInvalidTimePeriodException {
        if (!this.isExpired()) {
            this.setEnd(LocalDateTime.now());
        }
    }

    @Override
    public String toString() {
        return this.begin + " - " + this.end;
    }

    @Override
    public TimeInterval getInterval() {
        return TimeInterval.between(this.begin, this.end);
    }

    @Override
    public LocalTime getBeginTime() {
        return this.begin.toLocalTime();
    }

    @Override
    public LocalTime getEndTime() {
        return this.end.toLocalTime();
    }

    @Override
    public LocalDateTime getBegin() {
        return this.begin;
    }

    @Override
    public LocalDateTime getEnd() {
        return this.end;
    }

    @Override
    public List<DayOfWeek> getDaysOfWeek() {
        long days = this.getInterval().toDuration().toDays();
        if (days > 6) {
            return Arrays.asList(DayOfWeek.values());
        }
        List<DayOfWeek> ret = new ArrayList<>();
        LocalDate begin = this.begin.toLocalDate();
        LocalDate end = this.end.toLocalDate();
        for (; begin != end; begin = begin.plusDays(1)) {
            ret.add(begin.getDayOfWeek());
        }
        ret.add(end.getDayOfWeek());
        return ret;
    }
}
