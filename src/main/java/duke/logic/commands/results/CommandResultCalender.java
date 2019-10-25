package duke.logic.commands.results;

import duke.model.lists.EventList;

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

    @Override
    public void setEvents(EventList events) {
        this.events = events;
    }

    @Override
    public EventList getEvents() {
        return events;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
