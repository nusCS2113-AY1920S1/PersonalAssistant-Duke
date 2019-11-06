package sgtravel.logic.commands.results;

import sgtravel.model.lists.EventList;

/**
 * Interface representing a calender.
 */
public interface Calenderable {

    EventList getEvents();

    void setEvents(EventList events);
}
