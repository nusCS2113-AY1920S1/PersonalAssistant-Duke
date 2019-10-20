package Events.EventTypes.EventSubclasses.AssessmentSubclasses;

import Events.EventTypes.EventSubclasses.Assessment;

public class Recital extends Assessment {
    /**
     * Creates recital event with isDone boolean for reading from files
     * @param eventType type of event denoted by character
     */
    public Recital(String description, boolean isDone, String startDateAndTime, String endDateAndTime) {
        super(description, isDone, startDateAndTime, endDateAndTime, 'R');
    }

    /**
     * Creates recital without isDone boolean for user input (assumes event entered is incomplete)
     * @param eventType type of event denoted by character
     */
    public Recital(String description, String startDateAndTime, String endDateAndTime) {
        super(description, false, startDateAndTime, endDateAndTime, 'R');
    }
}
