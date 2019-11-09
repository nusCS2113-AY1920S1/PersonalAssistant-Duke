package sgtravel.model;

import sgtravel.commons.exceptions.AddListFailException;
import sgtravel.commons.exceptions.DuplicateRouteException;
import sgtravel.commons.exceptions.FileNotSavedException;
import sgtravel.commons.exceptions.NoRecentItineraryException;
import sgtravel.commons.exceptions.NoSuchItineraryException;
import sgtravel.commons.exceptions.OutOfBoundsException;
import sgtravel.commons.exceptions.ParseException;
import sgtravel.model.lists.EventList;
import sgtravel.model.lists.RouteList;
import sgtravel.model.lists.VenueList;
import sgtravel.model.locations.BusStop;
import sgtravel.model.planning.Itinerary;
import sgtravel.model.planning.Recommendation;
import sgtravel.model.profile.ProfileCard;
import sgtravel.model.transports.Route;
import sgtravel.model.transports.TransportationMap;

import java.util.HashMap;

/**
 * Interface which grants other components access to information from persistent storage.
 */
public interface Model {
    /**
     * Returns the list of Events.
     *
     * @return The list of Events.
     */
    EventList getEvents();

    /**
     * Replaces the events of this model with the new one.
     *
     * @param events The new events.
     */
    void setEvents(EventList events);

    /**
     * Returns map object.
     *
     * @return The Map object.
     */
    TransportationMap getMap();

    /**
     * Returns the map of all bus stops.
     *
     * @return The HashMap of bus stops.
     */
    HashMap<String, BusStop> getBusStops();

    /**
     * Returns the list of Routes.
     *
     * @return The list of Routes.
     */
    RouteList getRoutes();

    /**
     * Gets a specified Route.
     *
     * @param index The index of the Route.
     * @return The Route at the index.
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
     *
     * @return The list of Events.
     */
    VenueList getEventVenues();

    /**
     * Gets an itinerary with the given name.
     *
     * @param name The serial number of the Itinerary.
     * @return The itinerary.
     */
    Itinerary getItinerary(String name);

    /**
     * Returns the recommendation list object.
     *
     * @return The requested recommendations list.
     */
    Recommendation getRecommendations();

    /**
     * Returns the itinerary hash-map keyed by their names.
     * 
     * @return The list of saved itineraries.
     */
    HashMap<String,Itinerary> getItineraryTable();

    /**
     * Saves the most recent recommendation.
     * 
     * @param itinerary The recent recommendation.
     */
    void setRecentItinerary(Itinerary itinerary) throws AddListFailException;

    /**
     * Returns the recently recommended itinerary.
     *
     * @return The recent recommendation.
     */
    Itinerary getRecentItinerary() throws NoRecentItineraryException;

    /**
     * Stores a new itinerary to storage.
     * 
     * @param itinerary The itinerary to be saved.
     */
    void setNewItinerary(Itinerary itinerary) throws ParseException;

    /**
     * Stores recently recommended itinerary.
     * 
     * @param newName The new name for the itinerary.
     */
    void confirmRecentItinerary(String newName) throws AddListFailException;

    /**
     * Deletes the requested itinerary from storage.
     * 
     * @param name The name of the itinerary to be "done" (deleted).
     */
    void doneItinerary(String name) throws NoSuchItineraryException;

    /**
     * Returns profile of user.
     *
     * @return The profile of user.
     */
    ProfileCard getProfileCard();

    /**
     * Returns name of the user.
     *
     * @return The name of the user.
     */
    String getName();

    /**
     * Adds an Itinerary to the favourites.
     *
     * @param name The name of the Itinerary.
     * @param itinerary The Itinerary.
     * @throws NoSuchItineraryException If there Itinerary does not exist.
     */
    void addToFavourite(String name, Itinerary itinerary) throws NoSuchItineraryException;

    /**
     * Deletes a favourite Itinerary.
     *
     * @param name The name of the Itinerary.
     * @throws NoSuchItineraryException If the Itinerary cannot be found.
     */
    void deleteFavourite(String name) throws NoSuchItineraryException;
}
