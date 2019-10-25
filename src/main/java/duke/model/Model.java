package duke.model;

import duke.commons.exceptions.CorruptedFileException;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.FileNotSavedException;
import duke.logic.CreateMap;
import duke.model.lists.EventList;
import duke.model.lists.RouteList;
import duke.model.lists.TaskList;
import duke.model.lists.VenueList;
import duke.model.planning.Agenda;
import duke.model.planning.Itinerary;
import duke.model.transports.BusService;
import duke.model.locations.BusStop;
import duke.model.locations.Venue;

import java.util.List;

/**
 * Interface for SGTravel's model.
 */
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
     *
     * @param numberOfDays Number of days.
     * @param itinerary The itinerary to store in persistent storage.
     * @return List of Days.
     * @throws DukeException If number of days exceeds 7.
     */
    List<Agenda> getRecommendations(int numberOfDays, Itinerary itinerary) throws DukeException;

    /**
     * Returns the list of Routes.
     */
    RouteList getRoutes();

    /**
     * Saves the Model data in storage.
     *
     * @throws CorruptedFileException If the file is corrupted.
     * @throws FileNotSavedException If the file cannot be saved.
     */
    void save() throws CorruptedFileException, FileNotSavedException;

    /**
     * Returns a list of event venues.
     */
    VenueList getEventVenues();
}
