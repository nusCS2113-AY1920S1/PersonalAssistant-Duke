package Model_Classes;

import Enums.TimeUnit;
import java.util.Date;

/**
 * An object class representing types of tasks: meeting.
 * Stores the description and when the meeting happens.
 */
public class Meeting extends Task {
    private int duration = 0;
    private TimeUnit timeUnit;
    /**
     * Constructor for Meeting object
     * Takes in inputs for description of the meeting and the time the meeting occurs
     * @param description Description of the meeting
     * @param at Time the meeting happens
     */
    public Meeting(String description, Date at) {
        super(description, at);
        this.duration = 0;
        this.timeUnit = TimeUnit.unDefined;
    }

    /**
     * overload constructor for meeting class
     * duration is specified in this constructor
     * @param description description of the meeting
     * @param date date and time the meeting starts
     * @param duration duration of the meeting in numbers
     * @param unit unit of time the meeting is in (hours, minutes etc)
     */
    public Meeting (String description, Date date, int duration, TimeUnit unit) {
        super(description, date);
        this.duration = duration;
        this.timeUnit = unit;
    }

    /**
     * Returns a string that has the full description of the meeting including the occurrence time
     * @return A string indicating the task type, description and the occurrence of the task
     */
    @Override
    public String toString() {
        if (this.isFixedDuration()){
            return "[M]" + super.toString() + " (on: " + super.getDate() + ") (duration: " + duration + " " + timeUnit.toString() + ")";
        } else {
            return "[M]" + super.toString() + " (on: " + super.getDate() + ")";
        }
    }

    /**
     * checks if the meeting is fixed duration
     * @return true if meeting does not have an undefined time unit
     */
    public boolean isFixedDuration() {
        // undefined TimeUnit indicates that meeting is not fixed duration
        return !this.timeUnit.equals(TimeUnit.unDefined);
    }

    /**
     * sets the duration of the meeting
     * @param duration duration in numbers of the meeting
     * @param timeUnit time unit of the meeting (minutes, hours, days, months, undefined)
     */
    public void setDuration(int duration, TimeUnit timeUnit) {
        this.duration = duration;
        this.timeUnit = timeUnit;
    }

    /**
     * gets the duration of the meeting
     * @return duration of the meeting as a String.
     */
    public String getDuration() {
        return Integer.toString(duration);
    }

    /**
     * gets the time unit of the meeting
     * @return timeunit of the meeting
     */
    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

}
