package Events.EventTypes.EventSubclasses;

import Events.EventTypes.Event;

public abstract class Assessment extends Event {
    /**
     * Creates assessment event with isDone boolean for reading from files
     *
     * @param eventType type of event denoted by character
     */
    public Assessment(String description, boolean isDone, String startDateAndTime, String endDateAndTime, char eventType) {
        super(description, isDone, startDateAndTime, endDateAndTime, eventType);
    }

    /**
     * Creates assessment without isDone boolean for user input (assumes event entered is incomplete)
     *
     * @param eventType type of event denoted by character
     */
    public Assessment(String description, String startDateAndTime, String endDateAndTime, char eventType) {
        super(description, false, startDateAndTime, endDateAndTime, eventType);
    }
}
