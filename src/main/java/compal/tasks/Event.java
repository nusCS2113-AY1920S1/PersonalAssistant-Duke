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
     * @param time        Starting time of event.
     */
    public Event(String description, Priority priority, String date, String time) {
        super(description, priority);
        super.symbol = "E";
        super.setDate(date);
        super.setTime(time);
    }
}
