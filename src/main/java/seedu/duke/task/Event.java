package seedu.duke.task;

import java.util.Date;

/**
 * Event class is a typ of task with a date/time when the event is going to happen.
 */
public class Event extends Task {
    private Date time;

    /**
     * Instantiates the Event class with name and time. Time must be passed in during the instantiation as it
     * cannot be changed later.
     *
     * @param name name of the Event
     * @param time time of the Event that is going to happen
     */
    public Event(String name, Date time) {
        super(name);
        this.time = time;
        this.taskType = TaskType.Event;
    }

    /**
     * Converts the Event to a human readable string containing important information about the
     * Event, including the type and time of this Event.
     *
     * @return a human readable string containing the important information
     */
    @Override
    public String toString() {
        return "[E]" + this.getStatus() + " (at: " + formatDate() + ")";
    }

    /**
     * Outputs a string with all the information of this Event to be stored in a file for future
     * usage.
     *
     * @return a string containing all information of this Event
     */
    @Override
    public String toFileString() {
        return (this.isDone ? "1" : "0") + " event " + this.name + " /at "
                + formatDate();
    }

    /**
     * Outputs a formatted string of the time of this Event. The format is the same as input
     * format and is shared by all tasks.
     *
     * @return a formatted string of the time of this Event
     */
    protected String formatDate() {
        return format.format(this.time);
    }
}
