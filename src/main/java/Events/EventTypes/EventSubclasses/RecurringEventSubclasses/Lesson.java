package Events.EventTypes.EventSubclasses.RecurringEventSubclasses;

import Events.EventTypes.EventSubclasses.RecurrentEvent;

public class Lesson extends RecurrentEvent {
    /**
     * creates new lesson class with boolean to read from file
     */
    public Lesson(String description, boolean isDone, String startDateAndTime, String endDateAndTime) {
        super(description, isDone, startDateAndTime, endDateAndTime, 'L');
    }

    /**
     * creates new lesson class with boolean to read from user input (assume incomplete)
     */
    public Lesson(String description, String startDateAndTime, String EndDateAndTime) {
        super(description, false, startDateAndTime, EndDateAndTime, 'L');
    }
}
