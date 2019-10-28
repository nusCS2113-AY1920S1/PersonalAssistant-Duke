package duke.model;

import duke.commons.exceptions.CorruptedFileException;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.FileNotSavedException;
import duke.logic.TransportationMap;
import duke.model.lists.EventList;
import duke.model.lists.RouteList;
import duke.model.lists.VenueList;
import duke.model.planning.Agenda;
import duke.model.planning.Itinerary;
import duke.model.profile.ProfileCard;
import duke.model.transports.BusService;
import duke.model.locations.BusStop;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface which grants other components access to information from persistent storage.
 */
public interface Model {
    /**
     * Returns the list of events.
     */
    EventList getEvents();

    /**
     * Return map object.
     */
    TransportationMap getMap();

    /**
     * Returns the list of events that is sorted chronologically.
     */
    EventList getSortedList();

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

    /**
     * Returns profile of user.
     */
    ProfileCard getProfileCard();

    /**
     * Returns whether if the user is a new user.
     */
    boolean isNewUser();

    /**
     * Returns name of the user.
     */
    String getName();
}
