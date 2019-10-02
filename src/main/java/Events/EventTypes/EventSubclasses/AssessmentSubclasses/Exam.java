package Events.EventTypes.EventSubclasses.AssessmentSubclasses;

import Events.EventTypes.EventSubclasses.Assessment;

public class Exam extends Assessment {
    /**
     * Creates exam event with isDone boolean for reading from files
     */
    public Exam(String description, boolean isDone, String startDateAndTime, String endDateAndTime) {
        super(description, isDone, startDateAndTime, endDateAndTime, 'E');
    }

    /**
     * Creates exam without isDone boolean for user input (assumes event entered is incomplete)
     */
    public Exam(String description, String startDateAndTime, String endDateAndTime) {
        super(description, false, startDateAndTime, endDateAndTime, 'E');
    }
}
