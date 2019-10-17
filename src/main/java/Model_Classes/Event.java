package Model_Classes;

import Enums.Priority;

import java.util.Date;
/**
 * An object class representing types of tasks that are events.
 * Stores the description and when the event happens.
 */
public class Event extends Task {
    private Date at;

    /**
     * Constructor for Event object
     * Takes in inputs for description of the event and the time the event occurs
     * @param description Description of the event
     * @param at Time the event happens
     */
    public Event(String description, Date at) {
        super(description);
        this.at = at;
    }

    /**
     * Overload Constructor for the Event object
     * Takes in an input for the user assigned to the event object
     * @param description Description of the event
     * @param at Time the event happens
     * @param user User whom the task is assigned to
     */
    public Event(String description, Date at, String user) {
        super(description, user);
        this.at = at;
    }

    /**
     * Overload Constructor for Event object
     * Takes in inputs for description of the event and the time the event occurs
     * @param description Description of the event
     * @param at Time the event happens
     * @param done Whether the task is completed
     * @param priority Priority of the task.
     */
    public Event(String description, Date at, boolean done, Priority priority) {
        super(description, done, priority);
        this.at = at;
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
//    @Override
    public void snoozeYear(int amount) {
        this.at.setYear(this.at.getYear() + amount);;
    }

    /**
     * Snoozes the Event by set amount of months
     * @param amount number of months to snooze
     */
//    @Override
    public void snoozeMonth(int amount) {
        this.at.setMonth(this.at.getMonth() + amount);;
    }

    /**
     * Snoozes the Event by set amount of days
     * @param amount number of days to snooze
     */
//    @Override
    public void snoozeDay(int amount) {
        this.at.setDate(this.at.getDate() + amount);;
    }

    /**
     * Snoozes the Event by set amount of hours
     * @param amount number of hours to snooze
     */
//    @Override
    public void snoozeHour(int amount){
        this.at.setHours(this.at.getHours() + amount);
    }

    /**
     * Snoozes the Event by set amount of hours
     * @param amount number of minutes to snooze
     */
//    @Override
    public void snoozeMinute(int amount){
        this.at.setMinutes(this.at.getMinutes() + amount);
    }

    /**
     * Returns a string that has the full description of the vent including the occurrence time
     * @return A string indicating the task type, description and the occurrence of the task
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (on: " + at + ")";
    }
}
