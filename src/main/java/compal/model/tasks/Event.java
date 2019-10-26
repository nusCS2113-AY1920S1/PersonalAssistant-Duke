package compal.model.tasks;

/**
 * Represents event task type with a starting date and time.
 */
public class Event extends Task {

    /**
     * Constructs Event object.
     *
     * @param description Description of event.
     * @param mainDate        Starting date of event.
     * @param startTime   Starting time of event.
     * @param priority    priority level of task type.
     * @param endTime     End time of event.
     * @param trailingDate End date of event.
     */
    public Event(String description, Priority priority, String mainDate, String trailingDate, String startTime,
                 String endTime) {
        super(description, priority);
        super.symbol = "E";
        super.setMainDate(mainDate);
        super.setStartTime(startTime);
        super.setEndTime(endTime);
        super.setTrailingDate(trailingDate);
    }
}
