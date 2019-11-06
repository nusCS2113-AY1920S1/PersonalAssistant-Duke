package sgtravel.logic.commands.results;

import sgtravel.model.lists.EventList;

/**
 * Interface representing an event list.
 */
public interface Eventable {

    EventList getEvents();

    void setEvents(EventList events);
}
