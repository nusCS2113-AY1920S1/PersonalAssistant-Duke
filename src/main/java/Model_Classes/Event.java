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
    private Date end;

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

    public Event(String description, Date at, Date end) {
        super(description);
        this.at = at;
        this.end = end;
    }

    public Date checkDate() { return this.at; }

    /**
     * Returns a string that has the full description of the vent including the occurrence time
     * @return A string indicating the task type, description and the occurrence of the task
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (on: " + at + ")";
    }
}
