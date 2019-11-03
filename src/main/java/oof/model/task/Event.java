package oof.model.task;

/**
 * An Event object is a type of Task.
 */
public class Event extends Task {

    String startDateTime;
    String endDateTime;

    /**
     * Constructor for Event.
     *
     * @param description Details of the Task.
     * @param startDateTime Starting date and time of the Event.
     * @param endDateTime   Ending date and time of the Event.
     */
    public Event(String description, String startDateTime, String endDateTime) {
        super(description);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    /**
     * Converts a task object to string format for storage.
     * @return Task object in string format for storage.
     */
    @Override
    public String toStorageString() {
        String startDate = startDateTime.split(" ")[INDEX_DATE];
        String startTime = startDateTime.split(" ")[INDEX_TIME];
        String endDate = endDateTime.split(" ")[INDEX_DATE];
        String endTime = endDateTime.split(" ")[INDEX_TIME];
        return "EVENT" + DELIMITER + getStatusIcon() + DELIMITER + description + DELIMITER + startDate
                + DELIMITER + startTime + DELIMITER + endDate + DELIMITER + endTime  + DELIMITER;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startDateTime + " to: " + endDateTime + ")";
    }
}
