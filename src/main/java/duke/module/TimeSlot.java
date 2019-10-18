package duke.module;

import java.util.Date;

/**
 * Function contains all the details of a time slot that needs to be scheduled.
 */
public final class TimeSlot {
    /**
     * Start of the event.
     */
    private Date startTime;
    /**
     * End of the event.
     */
    private Date endTime;
    /**
     * Location of event.
     */
    private String location;
    /**
     * Name of the Being taken.
     * e.g: Soccer 1
     */
    private String className;

    /**
     * Constructor.
     *
     * @param start the specified start time
     * @param end   the specified end time
     * @param loc   the specified location of the event
     * @param name  the specified name of the event
     */
    public TimeSlot(final Date start, final Date end,
                     final String loc, final String name) {
        this.startTime = start;
        this.endTime = end;
        this.location = loc;
        this.className = name;
    }

    /**
     * Getter method to obtain the name of the class.
     * @return The name of the class
     */
    public String getClassName() {
        return this.className;
    }

    /**
     * Getter method to obtain the start time of the class.
     * @return The start time of the class
     */
    public Date getStartTime() {
        return this.startTime;
    }

    /**
     * Getter method to obtain the end time of the class.
     * @return The end time of the class
     */
    public Date getEndTime() {
        return this.endTime;
    }

    /**
     * Getter method to obtain the location where the class is held.
     * @return The location where the class is held
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Setter method to set the name of the class.
     * @param name The name of the class
     */
    public void setClassName(final String name) {
        this.className = name;
    }

    /**
     * Setter method to set the start time of the class.
     * @param date The start time of the class
     */
    public void setStartTime(final Date date) {
        this.startTime = date;
    }

    /**
     * Setter method to set the end time of the class.
     * @param date The end time of the class
     */
    public void setEndTime(final Date date) {
        this.endTime = date;
    }

    /**
     * Setter method to set the location where the class is held.
     * @param myLocation The location where the class is held
     */
    public void setLocation(final String myLocation) {
        this.location = myLocation;
    }

}
