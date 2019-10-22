package duke.logic.commands.results;

import duke.model.lists.EventList;

public interface Calenderable {

    EventList getEvents();

    void setEvents(EventList events);
}
