package task;

import java.io.Serializable;

/**
 * Task containing information of a deadline.
 */
public class Event extends Task implements Serializable {
    //protected String at;

    /**
     * Creates an Event instance and initialises the required attributes.
     * @param description Description of the event.
     * @param at Time of the event in format "dd/MM/yyyy HHmm".
     */
    public Event(String description, String at) {
        super(description);
        this.at = at;
        this.type = "E";
    }

    /**
     * Returns a string status of the Event task.
     * @return The task's status icon, description and eventtime.
     */
    @Override
    public String giveTask() {
        return "[E]" + super.giveTask() + "(at: " + at + ")";
    }


}
