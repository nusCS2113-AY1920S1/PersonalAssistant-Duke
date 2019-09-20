package Model_Classes;

import CustomExceptions.DukeException;

import java.util.Date;
/**
 * An object class representing types of tasks that are events.
 * Stores the description and when the event happens.
 */
public class Event extends Task {
    private Date by;

    /**
     * Constructor for Event object
     * Takes in inputs for description of the event and the time the event occurs
     * @param description Description of the event
     * @param by Time the event happens
     */
    public Event(String description, Date by)  {
        super(description);
        this.by = by;
    }

    /**
     * Returns a string that has the full description of the vent including the occurrence time
     * @return A string indicating the task type, description and the occurrence of the task
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (on: " + by + ")";
    }

}
