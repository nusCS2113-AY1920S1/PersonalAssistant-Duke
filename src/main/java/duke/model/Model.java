package duke.model;

import duke.commons.exceptions.DukeException;
import duke.logic.CreateMap;
import duke.model.events.Task;
import duke.model.lists.EventList;
import duke.model.lists.TaskList;
import duke.model.lists.VenueList;
import duke.model.planning.Day;
import duke.model.planning.Itinerary;
import duke.model.transports.BusService;
import duke.model.locations.BusStop;
import duke.model.locations.Venue;

import java.util.List;

public interface Model {
    /**
     * Returns the list of tasks.
     */
    TaskList getTasks();

    /**
     * Return map object.
     */
    CreateMap getMap();

    /**
     * Returns the list of tasks that contains a date.
     */
    List<Task> getFilteredList();

    /**
     * Returns the list of tasks that is sorted chronologically.
     */
    List<Task> getChronoSortedList();

    /**
     * Returns all the list of locations.
     */
    List<Venue> getLocationList();

    /**
     * Returns the list of tasks that is an Event.
     */
    EventList getEventList();

    /**
     * Returns the list of all bus stops.
     */
    List<BusStop> getBusStops();

    /**
     * Returns the list of all bus routes.
     */
    List<BusService> getBusService();

    /**
     * Returns the list of all attractions.
     * @return
     * @param numberOfDays
     * @param itinerary
     */
    List<Day> getRecommendations(int numberOfDays, Itinerary itinerary) throws DukeException;

    /**
     * Saves the Model data in storage.
     */
    void save() throws DukeException;

    /**
     * Returns a list of event venues.
     */
    VenueList getEventVenues();
}
