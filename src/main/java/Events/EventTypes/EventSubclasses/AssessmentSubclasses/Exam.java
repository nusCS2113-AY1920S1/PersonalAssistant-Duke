package Events.EventTypes.EventSubclasses.AssessmentSubclasses;

import Events.EventTypes.EventSubclasses.Assessment;

public class Exam extends Assessment {
    public Exam(String description, boolean isDone, String startDateAndTime, String endDateAndTime) {
        super(description, isDone, startDateAndTime, endDateAndTime, 'E');
    }

    /**
     * creates new lesson class with boolean to read from user input (assume incomplete)
     */
    public Exam(String description, String startDateAndTime, String EndDateAndTime) {
        super(description, false, startDateAndTime, EndDateAndTime, 'E');
    }
}
