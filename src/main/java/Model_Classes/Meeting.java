package Model_Classes;

import Enums.TimeUnit;
import java.util.Date;

/**
 * An object class representing types of tasks: meeting.
 * Stores the description and when the meeting happens.
 */
public class Meeting extends Task {
    private boolean isFixedDuration;
    private int duration = 0;
    private TimeUnit timeUnit = TimeUnit.unDefined;
    /**
     * Constructor for Meeting object
     * Takes in inputs for description of the meeting and the time the meeting occurs
     * @param description Description of the meeting
     * @param at Time the meeting happens
     */
    public Meeting(String description, Date at) {
        super(description, at);
        this.isFixedDuration = false;
    }

    public Meeting (String description, Date date, int duration, TimeUnit unit) {
        super(description, date);
        this.duration = duration;
        this.timeUnit = unit;
        this.isFixedDuration = true;
    }

    /**
     * Returns a string that has the full description of the meeting including the occurrence time
     * @return A string indicating the task type, description and the occurrence of the task
     */
    @Override
    public String toString() {
        if (isFixedDuration){
            return "[M]" + super.toString() + " (on: " + super.getDate() + ") (duration: " + duration + " " + timeUnit.toString() + ")";
        } else {
            return "[M]" + super.toString() + " (on: " + super.getDate() + ")";
        }
    }

    public boolean isFixedDuration() {
        return isFixedDuration;
    }

    public String getDuration() {
        return Integer.toString(duration);
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

}
