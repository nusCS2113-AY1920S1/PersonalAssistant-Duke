package duke.items;

import duke.exceptions.BadInputException;
import java.util.Calendar;
import java.util.Date;
import duke.items.Snooze;

/**
 * In addition to the deadline and done status (inherited from Task),
 * the Event has a start time (and later, end time) that is represented by a date class.
 * The save and print strings have been overridden to show more information.
 */
public class Event extends Task implements Snooze {

    private String start;
    private String end; //For later.
    private DateTime eventStartTime;
    private DateTime eventEndTime;

    /**
     * Deadline object has a "at" string as well as a Date objects for start and end times.
     */
    public Event(int index, String description, String time) throws BadInputException {
        super(index, description, TaskType.EVENT); //Using the Task constructor. isDone is set to false.
        try {
            String[] startEndTime = time.split(" to ", 2);
            this.start = startEndTime[0];
            this.end = startEndTime[1];
        } catch (Exception e) {
            throw new BadInputException("Improper datetime. "
                    + "Correct format: event <event name> /at <event start time> to <event end time>");
        }
        this.eventStartTime = new DateTime(start);
        this.eventEndTime = new DateTime(end);
    }

    public String getStart() {
        return start;
    }

    public String getEventTime() {
        return eventStartTime.returnFormattedDate();
    }

    @Override
    public void snooze() {
        Calendar newStartDate = Calendar.getInstance();
        newStartDate.setTime(eventStartTime.getAt());
        newStartDate.add(Calendar.DATE, 1);
        eventStartTime.setDate(newStartDate.getTime());

        Calendar newEndDate = Calendar.getInstance();
        newEndDate.setTime(eventEndTime.getAt());
        newEndDate.add(Calendar.DATE, 1);
        eventEndTime.setDate(newEndDate.getTime());
    }

    @Override
    public String saveDetailsString() {
        return super.saveDetailsString() + "/" + start;
    }

    @Override
    public String toString() {
        return super.toString() + " (at: " + eventStartTime.returnFormattedDate()
                + " to " + eventEndTime.returnFormattedDate() + ")";
    }
}