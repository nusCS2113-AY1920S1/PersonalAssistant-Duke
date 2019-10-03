package eggventory.items;

import eggventory.exceptions.BadInputException;
import java.util.Calendar;
import eggventory.enums.TaskType;

/**
 * In addition to the deadline and done status (inherited from Stock),
 * the Event has a start time (and later, end time) that is represented by a date class.
 * The save and print strings have been overridden to show more information.
 */
public class Event extends Task {

    private DateTime eventStartTime;
    private DateTime eventEndTime;

    /**
     * Deadline object has a "at" string as well as a Date objects for start and end times.
     */

    public Event(String description, DateTime start, DateTime end) throws BadInputException {
        super(description, TaskType.EVENT); //Using the Stock constructor. isDone is set to false.
        try {
            this.eventStartTime = start;
            this.eventEndTime = end;
        } catch (Exception e) {
            throw new BadInputException("Improper datetime. "
                    + "Correct format: event <event name> /at <event start time> to <event end time>");
        }
    }

    @Override
    public Calendar getEndDate() {
        return eventEndTime.getAt();
    }

    @Override
    public Calendar getDate() {
        return eventStartTime.getAt();
    }
    //    public Date getDate() {
    //        return eventStartTime.getAt();
    //    }


    public DateTime getEventStartTimeObj() {
        return eventStartTime;
    }

    public DateTime getEventEndTimeObj() {
        return eventEndTime;
    }

    @Override
    public String saveDetailsString() {
        return super.saveDetailsString() + "/" + eventStartTime.toString() + " to " + eventEndTime.toString();
    }

    @Override
    public String toString() {
        return super.toString() + " (at: " + eventStartTime.returnFormattedDate()
                + " to " + eventEndTime.returnFormattedDate() + ")";
    }
}