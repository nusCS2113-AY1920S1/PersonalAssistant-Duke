/**
 * Event object inherits Task.
 * Is a type of task available for use.
 */
public class Event extends Task {
    /**
    * Contains the date & time in a String.
    */
    protected String date;

    /**
     * Creates event
     * @param description Description of task.
     * @param date Event date & time.
     */
    public Event(String description, String date){
        super(description);
        this.date = date;
    }

    /**
     * Creates event with boolean attached, so as to read from file correctly.
     * @param description Description of task.
     * @param date Event date & time.
     * @param isDone Boolean defining if the task is completed.
     */
    public Event(String description, String date, boolean isDone){
        super(description, isDone);
        this.date = date;
    }

    /**
     * Converts event type task to string format for printing.
     * @return Formatted string representing the event, whether or not it is completed and its date.
     */
    @Override
    public String toString(){
        return "[E]" + super.toString() + "(at: " + date + ")";
    }
}
