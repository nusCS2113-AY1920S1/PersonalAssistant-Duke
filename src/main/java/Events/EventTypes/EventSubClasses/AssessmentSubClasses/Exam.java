package Events.EventTypes.EventSubClasses.AssessmentSubClasses;

import Events.EventTypes.EventSubClasses.Assessment;

public class Exam extends Assessment {
    /**
     * creates new lesson class with boolean to read from file
     */
    public Exam(String description, boolean isDone, String startDateAndTime, String endDateAndTime) {
        super(description, isDone, startDateAndTime, endDateAndTime, 'A');
    }

    /**
     * creates new lesson class with boolean to read from user input (assume incomplete)
     */
    public Exam(String description, String startDateAndTime, String EndDateAndTime) {
        super(description, false, startDateAndTime, EndDateAndTime, 'A');
    }
}
