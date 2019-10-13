package duke.modules;

import duke.exceptions.ModInvalidTimeException;
import duke.util.TimeInterval;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

public class TaskWithInterval extends Task {

    TimeInterval interval;

    public TaskWithInterval(String task, Period period, Duration duration) {
        super(task);
        this.interval = new TimeInterval(period, duration);
    }

    public TaskWithInterval(String task, LocalDateTime begin, LocalDateTime end) {
        super(task);
        this.interval = new TimeInterval(begin, end);
    }

    public TaskWithInterval(String task, TimeInterval interval) {
        super(task);
        this.interval = interval;
    }

    public TaskWithInterval(String task, Period period) {
        super(task);
        this.interval = new TimeInterval(period);
    }

    public TaskWithInterval(String task, Duration duration) {
        super(task);
        this.interval = new TimeInterval(duration);
    }

    public TaskWithInterval(String task) {
        super(task);
        this.interval = new TimeInterval();
    }

    public TimeInterval getInterval() {
        return this.interval;
    }

    public void setInterval(LocalDateTime begin, LocalDateTime end) {
        this.interval.setInterval(begin, end);
    }

    public void setInterval(Period period, Duration duration) {
        this.interval.setInterval(period, duration);
    }

    public void setInterval(TimeInterval interval) {
        this.interval.setInterval(interval);
    }

    /**
     * Parse String inputs to TimeInterval object.
     * @param days input days
     * @param hours input hours
     * @param minutes input minutes
     * @param seconds input seconds
     * @return equivalent TimeInterval object
     * @throws ModInvalidTimeException when input value is invalid
     */
    static TimeInterval parseInterval(String days, String hours, String minutes, String seconds)
            throws ModInvalidTimeException {
        TimeInterval interval = new TimeInterval();
        try {
            if (days != null) {
                interval = interval.plus(TimeInterval.ofDays(Integer.parseInt(days)));
            }
            if (hours != null) {
                interval = interval.plus(TimeInterval.ofHours(Integer.parseInt(hours)));
            }
            if (minutes != null) {
                interval = interval.plus(TimeInterval.ofMinutes(Integer.parseInt(minutes)));
            }
            if (seconds != null) {
                interval = interval.plus(TimeInterval.ofSeconds(Integer.parseInt(seconds)));
            }
        } catch (NumberFormatException ex) {
            throw new ModInvalidTimeException();
        }
        return interval;
    }
}
