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

    /**
     * Converts a task object to string format for storage.
     * @return Task object in string format for storage.
     */
    @Override
    public String toStorageString() {
        String startDateTime = startTime;
        String endDateTime = endTime;
        String startDate = startDateTime.split(" ")[DATE];
        String startTime = startDateTime.split(" ")[TIME];
        String endDate = endDateTime.split(" ")[DATE];
        String endTime = endDateTime.split(" ")[TIME];
        return "E" + DELIMITER + getStatusIcon() + DELIMITER + description + DELIMITER + startDate
                + DELIMITER + startTime + DELIMITER + endDate + DELIMITER + endTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startTime + " to: " + endTime + ")";
    }
}
