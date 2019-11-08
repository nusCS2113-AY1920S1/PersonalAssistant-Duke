package sgtravel.model;

import sgtravel.commons.exceptions.FileNotSavedException;
import sgtravel.commons.exceptions.NoRecentItineraryException;
import sgtravel.commons.exceptions.NoSuchItineraryException;
import sgtravel.commons.exceptions.OutOfBoundsException;
import sgtravel.commons.exceptions.ParseException;
import sgtravel.commons.exceptions.DuplicateRouteException;
import sgtravel.model.lists.EventList;
import sgtravel.model.lists.RouteList;
import sgtravel.model.lists.VenueList;
import sgtravel.model.locations.BusStop;
import sgtravel.model.planning.Itinerary;
import sgtravel.model.planning.Recommendation;
import sgtravel.model.profile.ProfileCard;
import sgtravel.model.transports.BusService;
import sgtravel.model.transports.Route;
import sgtravel.model.transports.TransportationMap;

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
     * Gets a specified Route.
     *
     * @param index The index of the Route.
     */
    Route getRoute(int index) throws OutOfBoundsException;

    /**
     * Adds a route to the list of routes.
     *
     * @param route The route to add.
     */
    void addRoute(Route route) throws DuplicateRouteException;

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

    /**
     * Shows the Itinerary specified by a give name.
     * @param name The serial number of the Itinerary.
     */
    Itinerary getItinerary(String name);

    /**
     * Returns the recommendation list object.
     * @return recommendations The requested recommendations list.
     */
    Recommendation getRecommendations();

    /**
     * Returns the itinerary hash-map keyed by their names.
     * @return itineraryTable The list of saved itineraries.
     */
    HashMap<String,Itinerary> getItineraryTable();

    /**
     * Saves the most recent recommendation.
     * @param itinerary The recent recommendation.
     */
    void setRecentItinerary(Itinerary itinerary);

    /**
     * Returns the recently recommended itinerary.
     * @return recentItinerary The recent recommendation.
     */
    Itinerary getRecentItinerary() throws NoRecentItineraryException;

    /**
     * Stores a new itinerary to storage.
     * @param itinerary The itinerary to be saved.
     */
    void setNewItinerary(Itinerary itinerary) throws ParseException;

    /**
     * Stores recently recommended itinerary.
     * @param newName The new name for the itinerary.
     */
    void confirmRecentItinerary(String newName);

    /**
     * Deletes the requested itinerary from storage.
     * @param name The name of the itinerary to be "done" (deleted).
     */
    void doneItinerary(String name) throws NoSuchItineraryException;

    /**
     * Returns profile of user.
     */
    ProfileCard getProfileCard();

    /**
     * Returns name of the user.
     */
    String getName();
}
