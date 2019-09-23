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
     * Creates event with two date input
     *
     * @param description event description
     * @param isDone      boolean representing state of event completion
     * @param startDateAndTime string representing start date of event
     * @param endDateAndTime string representing end date of event
     */
    public Event(String description, boolean isDone, String startDateAndTime, String endDateAndTime) {
        this.description = description;
        this.isDone = isDone;
        this.startDateObj = new DateObj(startDateAndTime);
        this.endDateObj = new DateObj(endDateAndTime);
    }

    /**
     * Creates event with one date input (e.g todo)
     *
     * @param description event description
     * @param dateAndTime string representing date of event
     */
    public Event(String description, String dateAndTime) {
        this.description = description;
        this.isDone = false;
        this.startDateObj = new DateObj(dateAndTime);
        this.endDateObj = null; //no end date, set to null
        this.eventType = 'T'; //event with no end date can only be todo type
    }

    /**
     * Creates event with two date input
     *
     * @param description event description
     * @param startDateAndTime string representing start date of event
     * @param endDateAndTime string representing end date of event
     */
    public Event(String description, String startDateAndTime, String endDateAndTime) {
        this.description = description;
        this.isDone = false;
        this.startDateObj = new DateObj(startDateAndTime);
        this.endDateObj = new DateObj(endDateAndTime);
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
