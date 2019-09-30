package compal.tasks;

/**
 * Represents event task type with a starting date and time.
 */
public class Event extends Task {

    /**
     * Constructs Event object.
     *
     * @param description Description of event.
     * @param date        Starting date of event.
     * @param startTime       Starting time of event.
     * @param priority    priority level of task type
     * @param endTime       End time of deadline
     */
    public Event(String description, Priority priority, String date, String startTime, String endTime) {
        super(description, priority);
        super.symbol = "E";
        super.setDate(date);
        super.setStartTime(startTime);
        super.setEndTime(endTime);
    }
}
