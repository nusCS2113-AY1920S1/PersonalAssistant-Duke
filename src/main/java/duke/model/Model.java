package duke.model;

import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.ParseException;
import duke.commons.exceptions.RouteDuplicateException;
import duke.model.lists.EventList;
import duke.model.lists.RouteList;
import duke.model.lists.VenueList;
import duke.model.locations.BusStop;
import duke.model.planning.Itinerary;
import duke.model.planning.Recommendation;
import duke.model.profile.ProfileCard;
import duke.model.transports.BusService;
import duke.model.transports.Route;
import duke.model.transports.TransportationMap;

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

    Itinerary getItinerary(String number) throws DukeException;

    Recommendation getRecommendations();

    HashMap<String,Itinerary> getItineraryTable();

    void storeRecentItinerary(Itinerary itinerary);

    Itinerary getRecentItinerary();

    void storeNewItinerary(Itinerary itinerary, String[] itineraryDetails) throws ParseException;

    void confirmRecentItinerary();

    /**
     * Returns profile of user.
     */
    ProfileCard getProfileCard();

    /**
     * Returns name of the user.
     */
    String getName();
}
