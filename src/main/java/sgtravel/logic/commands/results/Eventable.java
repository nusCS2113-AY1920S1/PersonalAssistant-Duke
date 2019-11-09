package sgtravel.logic.commands.results;

import sgtravel.model.lists.EventList;

/**
 * Interface representing an event list.
 */
public interface Eventable {

    /**
     * Gets the EventList.
     *
     * @return The EventList.
     */
    EventList getEvents();

    /**
     * Sets the EventList.
     *
     * @param events The EventList to set.
     */
    void setEvents(EventList events);
}
