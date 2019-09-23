package Events.EventTypes;

import Events.Formatting.DateObj;

/**
 * Represents a event that can be created, deleted and completed within the Duke program.
 * Is to be the parent class for all types of events available for Duke program.
 */
public abstract class Event {
    protected String description;
    protected boolean isDone;
    protected DateObj startDateObj;
    protected DateObj endDateObj;
    protected char eventType;

    /**
     * Creates event with start and end date.
     *
     * @param description      event description
     * @param isDone           boolean representing state of event completion
     * @param startDateAndTime string representing start date of event
     * @param endDateAndTime   string representing end date of event
     */
    public Event(String description, boolean isDone, String startDateAndTime, String endDateAndTime, char eventType) {
        this.description = description;
        this.isDone = isDone;
        this.eventType = eventType;
        this.startDateObj = new DateObj(startDateAndTime);
        this.endDateObj = new DateObj(endDateAndTime);
    }

    /**
     * Creates event with one date input (e.g todo)
     *
     * @param description event description
     * @param isDone      boolean representing state of event completion
     * @param dateAndTime string representing date of task
     */
    public Event(String description, boolean isDone, String dateAndTime) {
        this.description = description;
        this.isDone = isDone;
        this.startDateObj = new DateObj(dateAndTime);
        this.endDateObj = null; //no end date, set to null
        this.eventType = 'T'; //event with no end date can only be todo type
    }

    /**
     * Returns a tick if the event is completed, else returns a cross.
     *
     * @return Symbol tick or cross
     */
    public String getStatusIcon() {
        return (isDone) ? "✓" : "✗";
    }

    /**
     * Marks a event as completed
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Gets description of the event.
     *
     * @return event description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * returns formatted string of event, including status icon and description.
     *
     * @return string containing event in the format [(tick/cross)] (event description)
     */
    @Override
    public String toString() {
        if (endDateObj == null) {
            return "[" + this.eventType + "][" + this.getStatusIcon() + "] " +
                    this.description + "\nDate: " + startDateObj.toOutputString();
        } else {
            return "[" + this.eventType + "][" + this.getStatusIcon() + "] " +
                    this.description + "\nStart date: " + startDateObj.toOutputString() +
                    "\nEnd date: " + endDateObj.toOutputString();
        }
    }

    /**
     * Returns the type of event. May be overridden by subclasses.
     *
     * @return String representing the type of Event.
     */
    public String getType() {
        return "Event";
    }

    /**
     * This is overridden by the types of events that require the storage of dates.
     * The purpose of this method is to facilitate comparison of dates.
     *
     * @return startDateObj stored in the Event object.
     */
    public DateObj getStartDateObj() {
        return startDateObj;
    }

    /**
     * This is overridden by the types of events that require the storage of dates.
     * The purpose of this method is to facilitate comparison of dates.
     *
     * @return endDateObj stored in the Event object.
     */
    public DateObj getEndDateObj() {
        return endDateObj;
    }

    public String startDateToString() {
        return startDateObj.toOutputString();
    }
}
