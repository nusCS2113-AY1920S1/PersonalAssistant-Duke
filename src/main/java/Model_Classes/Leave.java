package Model_Classes;

import Enums.TimeUnit;
import java.util.Date;

/**
 * An object class representing tasks the are leaves.
 * Stores the description as well as the start and end time of the leave.
 */

public class Leave extends Task {
    private Date from;
    private Date to;
    private boolean isFixedDuration;
    private int duration;
    private TimeUnit timeUnit;
    private boolean isShort;


    public Leave(String description, Date from, Date to) {
        super(description);
        this.from = from;
        this.to = to;
        this.isShort = false;
    }

    public Leave(String description, Date from, int duration, TimeUnit unit) {
        super(description);
        this.from = from;
        this.duration = duration;
        this.timeUnit = unit;
        this.isShort = true;
    }

    public Date checkStartDate() {
        return this.from;
    }

    public Date checkEndDate() {
        return this.to;
    }

    public boolean isShort() {
        return isShort;
    }

    public String getDuration() {
        return Integer.toString(duration);
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    @Override
    public String toString() {
        if(isShort) {
            return "[L]" + super.toString() + "(from: " + from + " back in( " + duration + " " + timeUnit.toString() + ")";
        } else {
            return "[L]" + super.toString() + "(from: " + from  + " to: " + to + ")";
        }
    }
}
