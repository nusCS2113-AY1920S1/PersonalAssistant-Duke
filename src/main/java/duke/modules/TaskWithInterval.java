package duke.modules;

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
}
