package duke.logic.commands.results;

import duke.model.lists.EventList;

/**
 * Interface representing an event list.
 */
public interface Eventable {

    EventList getEvents();

    void setEvents(EventList events);
}
