package duchess.model;

import java.time.LocalDateTime;

public class TimeFrame implements Comparable<TimeFrame> {
    /**
     * Start and end points of the timeframe.
     */
    private LocalDateTime start;
    private LocalDateTime end;

    /**
     * Marks timeframe as indefinite, i.e. things that
     * don't have a definite start or end time.
     */
    private boolean isIndefinite;

    /**
     * Marks time frame as instantaneous.
     */
    private boolean isInstantaneous;

    /**
     * Creates a TimeFrame that represents an interval in time.
     *
     * @param start Starting time
     * @param end   Ending time
     */
    public TimeFrame(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
        this.isIndefinite = false;
        this.isInstantaneous = false;
    }

    private TimeFrame(LocalDateTime time) {
        this.start = time;
        this.end = time;
        this.isIndefinite = false;
        this.isInstantaneous = true;
    }

    private TimeFrame() {
        this.isIndefinite = true;
        this.isInstantaneous = false;
    }

    public static TimeFrame ofInstantaneousTask(LocalDateTime time) {
        return new TimeFrame(time);
    }

    public static TimeFrame ofTimelessTask() {
        return new TimeFrame();
    }

    /**
     * Returns true if this TimeFrame lies within the other TimeFrame.
     *
     * @param that the other TimeFrame
     * @return true if falls within
     */
    public boolean fallsWithin(TimeFrame that) {
        if (this.isIndefinite || that.isIndefinite) {
            return false;
        }

        return !(this.end.isBefore(that.start) || that.end.isBefore(this.start));
    }

    /**
     * Returns true if this TimeFrame clashes with the other TimeFrame.
     *
     * @param that the other TimeFrame
     * @return true if there is clash
     */
    public boolean clashesWith(TimeFrame that) {
        if (this.isInstantaneous || that.isInstantaneous) {
            return false;
        }

        return this.fallsWithin(that);
    }

    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Checks if TimeFrame belongs to task of type event.
     *
     * @return true if task is an event
     */
    public boolean hasDuration() {
        return !isIndefinite && !isInstantaneous;
    }

    @Override
    public int compareTo(TimeFrame that) {
        if (this.isIndefinite && that.isIndefinite) {
            return 0;
        } else if (this.isIndefinite) {
            return -1;
        } else if (that.isIndefinite) {
            return 1;
        } else if (!this.start.equals(that.start)) {
            return this.start.compareTo(that.start);
        } else {
            return this.end.compareTo(that.end);
        }
    }
}
