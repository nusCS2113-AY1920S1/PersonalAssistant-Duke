package planner.util.periods;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.List;

public class TimeInterval implements TemporalAmount, Serializable {
    private Period dateDuration;
    private Duration timeDuration;
    private static final double daysInYear = 365.2422;
    private static final int secondsInDay = 86400;
    public static final TimeInterval ZERO = new TimeInterval();

    public TimeInterval(Period dateDuration, Duration timeDuration) {
        this.setInterval(dateDuration, timeDuration);
    }

    public TimeInterval(TimePeriod timePeriod) {
        this.setInterval(timePeriod);
    }

    public TimeInterval(TemporalAmount temporalAmount) {
        this.setInterval(temporalAmount);
    }

    public TimeInterval(Temporal begin, Temporal end) {
        this.setInterval(begin, end);
    }

    public TimeInterval(Period dateDuration) {
        this(dateDuration, Duration.ZERO);
    }

    public TimeInterval(Duration timeDuration) {
        this(Period.ZERO, timeDuration);
    }

    public TimeInterval() {
        this(Period.ZERO, Duration.ZERO);
    }

    private void adjustDateTime() {
        Duration newTimeDuration;
        for (newTimeDuration = this.timeDuration.plusSeconds(TimeInterval.secondsInDay);
             newTimeDuration.isNegative();
             newTimeDuration = newTimeDuration.plusSeconds(TimeInterval.secondsInDay)) {
            this.dateDuration = this.dateDuration.minusDays(1);
            this.timeDuration = newTimeDuration;
        }
        for (newTimeDuration = this.timeDuration.minusSeconds(TimeInterval.secondsInDay);
             !newTimeDuration.isNegative();
             newTimeDuration = newTimeDuration.minusSeconds(TimeInterval.secondsInDay)) {
            this.dateDuration = this.dateDuration.plusDays(1);
            this.timeDuration = newTimeDuration;
        }
    }

    public Period getDateDuration() {
        this.adjustDateTime();
        return this.dateDuration;
    }

    public Duration getTimeDuration() {
        this.adjustDateTime();
        return this.timeDuration;
    }

    public void setDateDuration(Period dateDuration) {
        this.dateDuration = dateDuration;
    }

    public void setTimeDuration(Duration timeDuration) {
        this.timeDuration = timeDuration;
    }

    public void setInterval(TimeInterval timeInterval) {
        this.dateDuration = timeInterval.dateDuration;
        this.timeDuration = timeInterval.timeDuration;
    }

    public void setInterval(TimePeriod timePeriod) {
        this.setInterval(Duration.between(timePeriod.getBegin(), timePeriod.getEnd()));
    }

    public void setInterval(Temporal begin, Temporal end) {
        this.setInterval(TimeInterval.between(begin, end));
    }

    public void setInterval(TemporalAmount temporalAmount) {
        this.setInterval(Duration.from(temporalAmount));
    }

    public void setInterval(Duration timeDuration) {
        this.setInterval(Period.ZERO, timeDuration);
    }

    public void setInterval(Period dateDuration, Duration timeDuration) {
        this.dateDuration = dateDuration;
        this.timeDuration = timeDuration;
    }

    public Period toPeriod() {
        return this.getDateDuration();
    }

    /**
     * Convert the time stored in this object to an equivalent Duration object.
     * @return a Duration object which represent the total time with seconds
     */
    public Duration toDuration() { // Estimate
        double daysToAdd = TimeInterval.daysInYear * this.dateDuration.getYears()
                + TimeInterval.daysInYear * this.dateDuration.getMonths() / 12
                + this.dateDuration.getDays();
        double nanosToAdd = daysToAdd * TimeInterval.secondsInDay * 1000000000;
        return this.timeDuration.plusNanos((long)nanosToAdd);
    }

    public TimeInterval plus(Period dateDuration, Duration timeDuration) {
        return new TimeInterval(this.dateDuration.plus(dateDuration), this.timeDuration.plus(timeDuration));
    }

    public TimeInterval plus(TimeInterval other) {
        return this.plus(other.dateDuration, other.timeDuration);
    }

    public TimeInterval plus(Period dateDuration) {
        return this.plus(dateDuration, Duration.ZERO);
    }

    public TimeInterval plus(Duration timeDuration) {
        return this.plus(Period.ZERO, timeDuration);
    }

    public TimeInterval minus(Period dateDuration, Duration timeDuration) {
        return new TimeInterval(this.dateDuration.minus(dateDuration), this.timeDuration.minus(timeDuration));
    }

    public TimeInterval minus(TimeInterval other) {
        return this.minus(other.dateDuration, other.timeDuration);
    }

    public TimeInterval minus(Period dateDuration) {
        return this.minus(dateDuration, Duration.ZERO);
    }

    public TimeInterval minus(Duration timeDuration) {
        return this.minus(Period.ZERO, timeDuration);
    }

    public TimeInterval multipliedBy(int scalar) {
        return new TimeInterval(this.dateDuration.multipliedBy(scalar), this.timeDuration.multipliedBy(scalar));
    }

    public static TimeInterval between(Temporal begin, Temporal end) {
        return new TimeInterval(Period.ZERO, Duration.between(begin, end));
    }

    /**
     * Return the difference in time between two LocalDateTime epochs.
     * @param begin the 1st epoch
     * @param end the 2nd epoch
     * @return a TimeInterval object representing the difference between the two epochs.
     */
    public static TimeInterval between(LocalDateTime begin, LocalDateTime end) {
        Period dateDiff = Period.between(begin.toLocalDate(), end.toLocalDate());
        Duration timeDiff = Duration.between(begin.toLocalTime(), end.toLocalTime());
        return new TimeInterval(dateDiff, timeDiff);
    }

    public static TimeInterval between(LocalDateTime begin, LocalDate end) {
        return TimeInterval.between(begin, LocalDateTime.of(end, LocalTime.MIN));
    }

    public static TimeInterval between(LocalDate begin, LocalDateTime end) {
        return TimeInterval.between(LocalDateTime.of(begin, LocalTime.MIN), end);
    }

    public static TimeInterval between(LocalDate begin, LocalDate end) {
        return new TimeInterval(Period.between(begin, end));
    }

    public static TimeInterval between(LocalTime begin, LocalTime end) {
        return new TimeInterval(Duration.between(begin, end));
    }

    public boolean isZeroApprox() {
        return this.toDuration().isZero();
    }

    public boolean isZeroFrom(LocalDateTime localDateTime) {
        return localDateTime.plus(this).isEqual(localDateTime);
    }

    public boolean isZeroFromNow() {
        return this.isZeroFrom(LocalDateTime.now());
    }

    public boolean isNegativeApprox() {
        return this.toDuration().isNegative();
    }

    public boolean isNegativeFrom(LocalDateTime localDateTime) {
        return localDateTime.plus(this).isBefore(localDateTime);
    }

    public boolean isNegativeFromNow() {
        return this.isNegativeFrom(LocalDateTime.now());
    }

    public boolean isPositiveApprox() {
        Duration val = this.toDuration();
        return !(val.isZero() || val.isNegative());
    }

    public boolean isPositiveFrom(LocalDateTime localDateTime) {
        return localDateTime.plus(this).isAfter(localDateTime);
    }

    public boolean isPositiveFromNow() {
        return this.isPositiveFrom(LocalDateTime.now());
    }

    public boolean isLessThan(TimeInterval other) {
        return this.minus(other).isNegativeApprox();
    }

    public boolean isGreaterThan(TimeInterval other) {
        return !this.isLessThan(other);
    }

    public static TimeInterval ofNanos(int nanos) {
        return new TimeInterval(Duration.ofNanos(nanos));
    }

    public static TimeInterval ofMillis(int millis) {
        return new TimeInterval(Duration.ofMillis(millis));
    }

    public static TimeInterval ofSeconds(int seconds) {
        return TimeInterval.ofSeconds(seconds, 0);
    }

    public static TimeInterval ofSeconds(int seconds, int nanoAdjustment) {
        return new TimeInterval(Duration.ofSeconds(seconds, nanoAdjustment));
    }

    public static TimeInterval ofMinutes(int minutes) {
        return new TimeInterval(Duration.ofMinutes(minutes));
    }

    public static TimeInterval ofHours(int hours) {
        return new TimeInterval(Duration.ofHours(hours));
    }

    public static TimeInterval ofDays(int days) {
        return new TimeInterval(Period.ofDays(days));
    }

    public static TimeInterval ofWeeks(int weeks) {
        return new TimeInterval(Period.ofWeeks(weeks));
    }

    public static TimeInterval ofMonths(int months) {
        return new TimeInterval(Period.ofMonths(months));
    }

    public static TimeInterval ofYears(int years) {
        return new TimeInterval(Period.ofYears(years));
    }

    public static TimeInterval min(TimeInterval timeInterval1, TimeInterval timeInterval2) {
        return timeInterval1.isGreaterThan(timeInterval2) ? timeInterval2 : timeInterval1;
    }

    public static TimeInterval max(TimeInterval timeInterval1, TimeInterval timeInterval2) {
        return timeInterval1.isGreaterThan(timeInterval2) ? timeInterval1 : timeInterval2;
    }

    @Override
    public long get(TemporalUnit temporalUnit) {
        try {
            return this.dateDuration.get(temporalUnit);
        } catch (UnsupportedTemporalTypeException ex) {
            return this.timeDuration.get(temporalUnit);
        }
    }

    @Override
    public List<TemporalUnit> getUnits() {
        List<TemporalUnit> units = this.dateDuration.getUnits();
        units.addAll(this.timeDuration.getUnits());
        return units;
    }

    @Override
    public Temporal addTo(Temporal temporal) {
        return this.timeDuration.addTo(this.dateDuration.addTo(temporal));
    }

    @Override
    public Temporal subtractFrom(Temporal temporal) {
        return this.timeDuration.subtractFrom(this.dateDuration.subtractFrom(temporal));
    }

    public String toString() {
        return this.toDuration().toString();
    }

    /**
     * Convert to readable string instead of default string.
     * @return human-readable string representation
     */
    public String toReadableString() {
        return this.toString().substring(2)
                .replace("D", "Days")
                .replace("H", "Hours")
                .replace("M", "Minutes")
                .replace("S", "Seconds");
    }
}
