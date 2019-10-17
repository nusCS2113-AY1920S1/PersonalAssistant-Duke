package Events.EventTypes;

import Events.Formatting.EventDate;
import java.util.Comparator;

/**
 * Model_Class.Event object inherits Model_Class.Task.
 * Is a type of task available for use.
 */
public abstract class Event implements Comparable<Event>{
    protected String description;
    protected boolean isDone;
    protected EventDate startEventDate;
    protected EventDate endEventDate;
    protected char eventType;

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
        this.startEventDate = new EventDate(dateAndTime);
        this.endEventDate = null; //no end date, set to null
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
    public Event(String description, boolean isDone, String startDateAndTime, String endDateAndTime, char eventType) {
        this.description = description;
        this.isDone = isDone;
        this.startEventDate = new EventDate(startDateAndTime);
        this.endEventDate = new EventDate(endDateAndTime);
        this.eventType = eventType;
    }

    /**
     * Edit event with new description and two date input
     *
     * @param newDescription new event description
     */
    public void editEvent(String newDescription) {
        this.description = newDescription;
    }

    /**
     * Converts event type task to string format for printing.
     *
     * @return Formatted string representing the event, whether or not it is completed and its date.
     */
    public String toString() {
        if (getType() == 'T') { //if todo, then only one date entry
            return "[" + getDoneSymbol() + "][T] " + getDescription() + " BY: " + this.getStartDate().getFormattedDateString();
        } else { //multiple date entries
            return "[" + getDoneSymbol() + "][" + getType() + "] " +
                    getDescription() + " START: " + startEventDate.getFormattedDateString() +
                    " END: " + endEventDate.getFormattedDateString();
        }
    }

    public String toStringForFile() { //string that is to be saved to file.
        if (getEndDate() == null) {
            return getDoneSymbol() + getType() + " " + getDescription() + " " +
                    getStartDate().getUserInputDateString();
        }
        return getDoneSymbol() + getType() + " " + getDescription() + " " +
                getStartDate().getUserInputDateString() + " " + getEndDate().getUserInputDateString();
    }
    
    public char getType() {
    	return eventType;
    }

    public EventDate getStartDate() {
        return startEventDate;
    }

    public EventDate getEndDate() {
        return endEventDate;
    }

    public String getDescription(){
        return description;
    }

    public String getDoneSymbol() {
        return (isDone) ? "✓" : "✗";
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void rescheduleStartDate(EventDate newStartDate) {
        this.startEventDate = newStartDate;
    }

    public void rescheduleEndDate(EventDate newEndDate) {
        this.endEventDate = newEndDate;
    }

    @Override
    public int compareTo(Event currEvent) {
        if (this.startEventDate.getEventJavaDate().compareTo(currEvent.startEventDate.getEventJavaDate()) > 0) {
            return 1;
        } else {
            return -1;
        }
    }
}
