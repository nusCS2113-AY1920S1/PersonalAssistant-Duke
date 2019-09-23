package Events.EventTypes;

import Events.Formatting.DateObj;

/**
 * Model_Class.Event object inherits Model_Class.Task.
 * Is a type of task available for use.
 */
public abstract class Event {
    DateObj startDate;
    DateObj endDate;
    protected String description;
    protected boolean isDone;
    protected DateObj startDateObj;
    protected DateObj endDateObj;
    protected char eventType;

    /**
     * Contains the date and time in a DateObj.
     */
    protected String date;

    /**
     * Creates event with one date input (e.g todo)
     *
     * @param description event description
     * @param isDone      boolean representing state of event completion
     * @param dateAndTime string representing date of event
     */
    public Event(String description, boolean isDone, String dateAndTime) {
        this.description = description;
        this.isDone = isDone;
        this.startDateObj = new DateObj(dateAndTime);
        this.endDateObj = null; //no end date, set to null
        this.eventType = 'T'; //event with no end date can only be todo type
    }

    /**
     * Creates event
     *
     * @param description Description of task.
     * @param date        Event date and time.
     */
    public Event(String description, String date) {
        super(description);
        this.date = new DateObj(date).formatDate();
        this.dateObj = new DateObj(date);
    }

    /**
     * Creates event with boolean attached, so as to read from file correctly.
     *
     * @param description Description of task.
     * @param date        Event date and time.
     * @param isDone      Boolean defining if the task is completed.
     */
    public Event(String description, String date, boolean isDone) {
        super(description, isDone);
        this.date = new DateObj(date).formatDate();
        this.dateObj = new DateObj(date);
    }

    /**
     * Converts event type task to string format for printing.
     *
     * @return Formatted string representing the event, whether or not it is completed and its date.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + "(at: " + date + ")";
    }

    public String getDate() {
        return date;
    }
    
    public String getType() {
    	return "Event";
    }
    
    /**
     * Returns the DateObj stored in the Event object. This is to facilitate comparison of dates.
     * @return the DateObj stored in the Event object.
     */
    public DateObj getDateObj() {
    	return this.dateObj;
    }

    public String startDateToString() {
        return startDateObj.toOutputString();
    }
}
