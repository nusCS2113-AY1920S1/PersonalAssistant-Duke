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
     * @param sTime        Starting time of event.
     * @param priority    priority level of task type
     * @param eTime       End time of deadline
     */
    public Event(String description, Priority priority, String date, String sTime,String eTime) {
        super(description, priority);
        super.symbol = "E";
        super.setDate(date);
        super.setStartTime(sTime);
        super.setEndTime(eTime);
    }
}
