package Events.EventTypes.EventSubclasses.AssessmentSubclasses;

import Events.EventTypes.EventSubclasses.Assessment;

public class Recital extends Assessment {

    public Recital(String description, boolean isDone, String startDateAndTime, String endDateAndTime) {
        super(description, isDone, startDateAndTime, endDateAndTime, 'R');
    }

    /**
     * creates new lesson class with boolean to read from user input (assume incomplete)
     */
    public Recital(String description, String startDateAndTime, String EndDateAndTime) {
        super(description, false, startDateAndTime, EndDateAndTime, 'R');
    }
}
