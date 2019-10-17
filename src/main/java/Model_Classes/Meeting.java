package Model_Classes;

import Enums.Priority;
import Enums.TimeUnit;

import java.util.Date;
/**
 * An object class representing types of tasks that are events.
 * Stores the description and when the event happens.
 */
public class Meeting extends Task {
    private Date at;
    private boolean isFixedDuration;
    private String duration;
    private TimeUnit timeUnit;
    /**
     * Constructor for Event object
     * Takes in inputs for description of the event and the time the event occurs
     * @param description Description of the event
     * @param at Time the event happens
     */
    public Meeting(String description, Date at) {
        super(description);
        this.at = at;
        this.isFixedDuration = false;
    }

    public Meeting (String description, String duration, TimeUnit unit) {
        super(description);
        this.duration = duration;
        this.timeUnit = unit;
        this.isFixedDuration = true;
    }

    /**
     * Returns date of Event
     * @return date of Event
     */
    public Date checkDate() { return this.at; }

    /**
     * Snoozes the Event by set amount of years
     * @param amount number of years to snooze
     */
    @Override
    public void snoozeYear(int amount) {
        this.at.setYear(this.at.getYear() + amount);;
    }

    /**
     * Snoozes the Event by set amount of months
     * @param amount number of months to snooze
     */
    @Override
    public void snoozeMonth(int amount) {
        this.at.setMonth(this.at.getMonth() + amount);;
    }

    /**
     * Snoozes the Event by set amount of days
     * @param amount number of days to snooze
     */
    @Override
    public void snoozeDay(int amount) {
        this.at.setDate(this.at.getDate() + amount);;
    }

    /**
     * Snoozes the Event by set amount of hours
     * @param amount number of hours to snooze
     */
    @Override
    public void snoozeHour(int amount){
        this.at.setHours(this.at.getHours() + amount);
    }

    /**
     * Snoozes the Event by set amount of hours
     * @param amount number of minutes to snooze
     */
    @Override
    public void snoozeMinute(int amount){
        this.at.setMinutes(this.at.getMinutes() + amount);
    }

    /**
     * Returns a string that has the full description of the vent including the occurrence time
     * @return A string indicating the task type, description and the occurrence of the task
     */
    @Override
    public String toString() {
        if (isFixedDuration) {
            return "[M]" + super.toString() + " (in: " + duration + " " + timeUnit.toString() + ")";
        } else {
            return "[M]" + super.toString() + " (on: " + at + ")";
        }
    }

    public boolean isFixedDuration() {
        return isFixedDuration;
    }

    public String getDuration() {
        return duration;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
