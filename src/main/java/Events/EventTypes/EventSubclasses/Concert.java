package Events.EventTypes.EventSubclasses;

import Events.EventTypes.Event;

public class Concert extends Event {
    /**
     * creates new lesson class with boolean to read from file
     */
    public Concert(String description, boolean isDone, String startDateAndTime, String endDateAndTime) {
        super(description, isDone, startDateAndTime, endDateAndTime, 'C');
    }

    /**
     * creates new lesson class without boolean to read from user input (assumes incomplete)
     */
    public Concert(String description, String startDateAndTime, String endDateAndTime) {
        super(description, false, startDateAndTime, endDateAndTime, 'C');
    }
}