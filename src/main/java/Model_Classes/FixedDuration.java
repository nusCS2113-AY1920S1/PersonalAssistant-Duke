package Model_Classes;

import Enums.TimeUnit;
import java.util.Date;

/**
 * An object class representing types of event tasks that have a fixed duration
 * Stores the duration as a numerical value and a unit of time.
 */

public class FixedDuration extends Event {
    private int duration;
    private Date at;

    /**
     * Constructor for fixed duration object
     * Takes in the numerical value and the time unit in seconds, minutes or hours
     * @param description Description of the event
     * @param at Date and time at which the event happens
     * @param duration numerical value of time
     * @param timeUnit the unit of time to be measured in
     */
    public FixedDuration(String description, Date at, int duration, TimeUnit timeUnit) {
        super(description, at);
        this.duration = duration;
    }

    /**
     * Returns duration
     * @return duration of event in DD/MM/YYYY format
     */
    public int getDuration(){
        return duration;
    }

    /**
     * Returns a string that has the full description of the vent including the occurrence time
     * @return A string indicating the task type, description and the occurrence of the task
     */
    @Override
    public String toString() {
        return super.toString() + " (done in: " + duration + " hours)"; }
}
