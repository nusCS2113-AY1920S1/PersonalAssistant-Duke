package Model_Classes;

import CustomExceptions.DukeException;
import Enums.ExceptionType;
import Operations.CheckAnomaly;
import Operations.TaskList;

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

    public Event(String description) {
        super(description);
    }


    public Date checkDate() { return this.at; }

    @Override
    public void snoozeYear(int amount) {
        this.at.setYear(this.at.getYear() + amount);;
    }

    @Override
    public void snoozeMonth(int amount) {
        this.at.setMonth(this.at.getMonth() + amount);;
    }

    @Override
    public void snoozeDay(int amount) {
        this.at.setDate(this.at.getDate() + amount);;
    }

    @Override
    public void snoozeHour(int amount){
        this.at.setHours(this.at.getHours() + amount);
    }

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
        return "[E]" + super.toString() + " (on: " + at + ")";
    }
}
