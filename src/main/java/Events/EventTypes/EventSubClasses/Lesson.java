package Events.EventTypes.EventSubClasses;

public class Lesson extends RecurrentEvent {
    /**
     * creates new lesson class with boolean to read from file
     */
    public Lesson(String description, boolean isDone, String startDate, String endDate) {
        super(description, isDone, startDate, endDate, 'L');
    }

    /**
     * creates new lesson class with boolean to read from user input (assume incomplete)
     */
    public Lesson(String description, String startDate, String endDate) {
        super(description, false, startDate, endDate, 'L');
    }
}
