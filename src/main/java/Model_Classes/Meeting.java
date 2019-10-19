package Model_Classes;

import Enums.TimeUnit;
import java.util.Date;

/**
 * An object class representing types of tasks that are events.
 * Stores the description and when the event happens.
 */
public class Meeting extends Task {
    private boolean isFixedDuration;
    private int duration = 0;
    private TimeUnit timeUnit = TimeUnit.unDefined;
    /**
     * Constructor for Event object
     * Takes in inputs for description of the event and the time the event occurs
     * @param description Description of the event
     * @param at Time the event happens
     */
    public Meeting(String description, Date at) {
        super(description, at);
        this.isFixedDuration = false;
    }

    public Meeting (String description, int duration, TimeUnit unit) {
        super(description, null);
        this.duration = duration;
        this.timeUnit = unit;
        this.isFixedDuration = true;
    }

    /**
     * Returns a string that has the full description of the vent including the occurrence time
     * @return A string indicating the task type, description and the occurrence of the task
     */
    @Override
    public String toString() {
        if (isFixedDuration){
            return "[M]" + super.toString() + " (in: " + duration + " " + timeUnit.toString() + ")";
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
