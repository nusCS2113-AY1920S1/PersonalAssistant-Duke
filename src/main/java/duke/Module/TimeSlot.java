package duke.Module;

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
        startTime = start;
        endTime = end;
        location = loc;
        className = name;
    }

    public String getClassName() {
        return className;
    }

}
