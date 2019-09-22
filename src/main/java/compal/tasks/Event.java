package compal.tasks;

public class Event extends Task {

    /**
     * Store the event type task. Event refers object with deadline such as,
     * meetings and what not.
     *
     * @param description Description of the event to be stored
     * @param date        of the event to be stored
     */
    public Event(String description, String date, String time) {
        super(description);
        super.symbol = "E";
        super.setDate(date);
        super.setTime(time);
    }


}
