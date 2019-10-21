package oof.model.task;

/**
 * An Event object is a type of Task.
 */
public class Event extends Task {

    protected String startTime;
    protected String endTime;

    /**
     * Constructor for Event.
     *
     * @param description Details of the Task.
     * @param startTime Starting date and time of the Event.
     * @param endTime   Ending date and time of the Event.
     */
    public Event(String description, String startTime, String endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startTime + " to: " + endTime + ")";
    }
}
