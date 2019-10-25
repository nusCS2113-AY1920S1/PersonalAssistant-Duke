package duke.logic.commands.results;

import duke.model.lists.EventList;

/**
 * Interface representing a calender.
 */
public interface Calenderable {

    EventList getEvents();

    void setEvents(EventList events);
}
