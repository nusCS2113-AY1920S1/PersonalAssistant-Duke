package oof.task;

/**
 * An Event object is a type of Task.
 */
public class Event extends Task {

    private String startTiming;
    private String endTiming;

    /**
     * Constructor for Event.
     *
     * @param description Details of the Task.
     * @param startTiming Starting date and time of the Event.
     * @param endTiming   Ending date and time of the Event.
     */
    public Event(String description, String startTiming, String endTiming) {
        super(description);
        this.startTiming = startTiming;
        this.endTiming = endTiming;
    }

    public String getStartTiming() {
        return startTiming;
    }

    public String getEndTiming() {
        return endTiming;
    }

    public void setStartTiming(String newStartTiming) {
        this.startTiming = newStartTiming;
    }

    public void setEndTiming(String newEndTiming) {
        this.endTiming = newEndTiming;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startTiming + " to: " + endTiming + ")";
    }
}
