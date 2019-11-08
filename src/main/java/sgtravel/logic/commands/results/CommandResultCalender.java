package sgtravel.logic.commands.results;

import sgtravel.model.lists.EventList;

/**
 * Defines the command result of any calender command.
 */
public class CommandResultCalender extends CommandResult implements Calenderable {
    private String message;
    private EventList events;

    /**
     * Constructs a basic CommandResultCalender object.
     *
     * @param message Message for ui to display.
     */
    public CommandResultCalender(String message) {
        this.message = message;
    }

    /**
     * Sets the EventList.
     *
     * @param events The EventList to set.
     */
    @Override
    public void setEvents(EventList events) {
        this.events = events;
    }

    /**
     * Gets the EventList.
     *
     * @return events The EventList in this object.
     */
    @Override
    public EventList getEvents() {
        return events;
    }

    /**
     * Gets the message.
     *
     * @return message The message in this object.
     */
    @Override
    public String getMessage() {
        return message;
    }
}
