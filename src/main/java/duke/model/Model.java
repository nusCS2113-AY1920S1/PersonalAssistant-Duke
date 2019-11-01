package duke.model;

import duke.commons.exceptions.CorruptedFileException;
import duke.commons.exceptions.DukeDateTimeParseException;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.FileLoadFailException;
import duke.commons.exceptions.FileNotSavedException;
import duke.model.transports.TransportationMap;
import duke.commons.exceptions.RouteDuplicateException;
import duke.model.lists.EventList;
import duke.model.lists.RouteList;
import duke.model.lists.VenueList;
import duke.model.planning.Agenda;
import duke.model.planning.Itinerary;
import duke.model.profile.ProfileCard;
import duke.model.transports.BusService;
import duke.model.locations.BusStop;
import duke.model.transports.Route;

import java.util.HashMap;
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
     * Replaces the events of this model with the new one.
     * @param events The new events.
     */
    void setEvents(EventList events);

    /**
     * Returns map object.
     */
    TransportationMap getMap();

    /**
     * Returns the list of events that is sorted chronologically.
     */
    EventList getSortedList();

    /**
     * Returns the map of all bus stops.
     */
    HashMap<String, BusStop> getBusStops();

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
     */
    List<Agenda> getRecommendations(int numberOfDays, Itinerary itinerary) throws FileLoadFailException, FileNotSavedException;

    /**
     * Returns the list of Routes.
     */
    RouteList getRoutes();

    /**
     * Adds a route to the list of routes.
     *
     * @param route The route to add.
     */
    void addRoute(Route route) throws RouteDuplicateException;

    /**
     * Saves the Model data in storage.
     *
     * @throws FileNotSavedException If the file cannot be saved.
     */
    void save() throws FileNotSavedException;

    /**
     * Returns a list of event venues.
     */
    VenueList getEventVenues();

    void saveItinerary(Itinerary itinerary) throws FileNotSavedException;

    void itineraryListSave(Itinerary itinerary) throws FileNotSavedException;

    String listItineraries() throws FileLoadFailException;

    Itinerary getItinerary(String number) throws FileLoadFailException;

    Itinerary readRecommendations() throws FileLoadFailException;

    /**
     * Returns profile of user.
     */
    ProfileCard getProfileCard();

    /**
     * Returns name of the user.
     */
    String getName();
}
