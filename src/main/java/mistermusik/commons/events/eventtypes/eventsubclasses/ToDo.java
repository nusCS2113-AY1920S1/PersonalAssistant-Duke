package mistermusik.commons.events.eventtypes.eventsubclasses;

import mistermusik.commons.events.eventtypes.Event;

public class ToDo extends Event {
    /**
     * Creates ToDo with description and done boolean for reading from file.
     */
    public ToDo(String description, boolean isDone, String dateAndTime) {
        super(description, isDone, dateAndTime);
    }

    /**
     * Creates ToDo without boolean, assumed incomplete read from user input
     */
    public ToDo(String description, String dateAndTime) {
        super(description, false, dateAndTime);
    }

    /**
     * @return String containing information for user.
     */
    @Override
    public String toString() {
        return "[" + getDoneSymbol() + "][T] " + getDescription() + " BY: " + this.getStartDate().getFormattedDateString();
    }
}
