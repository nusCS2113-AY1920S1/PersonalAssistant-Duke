package duke.tasks;

import java.time.LocalDateTime;

/**
 * A class inheriting from duke.tasks.Task used to represent tasks that have both a description and an
 * associated location.
 */
public class Event extends Task {
    
    private String at;
    protected String end;
    protected LocalDateTime endDate;
    protected boolean date = false;
    protected String start;
    protected LocalDateTime startDate;

    @Override
    public String getType() {
        return "E";
    }

    /**
     * Constructor for the duke.tasks.Event object, which consists of the description of a task and a
     * start and end date that is associated with it.
     *
     * @param description the description of the task
     * @param start the start date of the event
     * @param end the end date of the event
     */
    public Event(String description, String start, String end) {
        super(description);
        this.end = end;
        this.start = start;
    }

    /**
     *  Constructor for the duke.tasks.Event object, which consists of the description of a task and a
     *  start date, end date, start time and end time that is associated with it.
     * @param description the description of the task
     * @param startDate the start date of the event
     * @param endDate the end date of the event
     * @param commandStart the start time of the event
     * @param commandEnd the end time of the event
     */
    public Event(String description, LocalDateTime startDate,LocalDateTime endDate, String commandStart,
                 String commandEnd) {
        super(description);
        endDate = endDate;
        startDate = startDate;
        date = true;
        this.end = commandEnd.trim();
        this.start = commandStart.trim();
    }


    /**
     * Returns a String representation of the duke.tasks.Event object, displaying its type (duke.tasks.Event),
     * description and the location associated with it.
     *
     * @return a String representation of the duke.tasks.Event object
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start.trim() + " to " + end.trim() + ")";
    }

    public boolean hasDate() {
        return date;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

}