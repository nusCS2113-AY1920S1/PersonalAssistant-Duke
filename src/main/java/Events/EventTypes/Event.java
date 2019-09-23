package Events.EventTypes;

import Events.Formatting.DateObj;

/**
 * Represents a event that can be created, deleted and completed within the Duke program.
 * Is to be the parent class for all types of events available for Duke program.
 */
public abstract class Event {
    DateObj startDate;
    DateObj endDate;
    protected String description;
    protected boolean isDone;

    /**
     * Creates event using description.
     *
     * @param description description of event
     */
    public Event(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Creates event including boolean representing whether or not the event is completed.
     *
     * @param description event description
     * @param isDone      boolean representing state of event completion
     */
    public Event(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
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
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

    public abstract String getDate();
   
    /**
     * Returns the type of event. May be overridden by subclasses.
     * @return String representing the type of Event.
     */
    public String getType() {
        return "Event";
    }
    
    /**
     * Returns the DateObj stored in the Event object.
     * This is overridden by the types of events that require the storage of dates.
     * The purpose of this method is to facilitate comparison of dates.
     * @return the DateObj stored in the Event object.
     */
    public DateObj getDateObj() {
    	return null;
    }
}
