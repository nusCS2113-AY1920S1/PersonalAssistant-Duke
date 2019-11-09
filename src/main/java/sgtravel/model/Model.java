package sgtravel.model;

import sgtravel.commons.exceptions.FileNotSavedException;
import sgtravel.commons.exceptions.NoRecentItineraryException;
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
import sgtravel.model.transports.Route;
import sgtravel.model.transports.TransportationMap;

import java.util.HashMap;

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
     *
     * @param events The new events.
     */
    void setEvents(EventList events);

    /**
     * Returns map object.
     */
    TransportationMap getMap();

    /**
     * Returns the map of all bus stops.
     */
    HashMap<String, BusStop> getBusStops();

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
     * Gets an itinerary with the given name.
     *
     * @param name The name of the itinerary.
     * @return The itinerary.
     */
    Itinerary getItinerary(String name);

    /**
     * Gets a Recommendation.
     *
     * @return The Recommendation.
     */
    Recommendation getRecommendations();

    /**
     * Gets the Itinerary table.
     *
     * @return The Itinerary table.
     */
    HashMap<String,Itinerary> getItineraryTable();

    /**
     * Sets the most recent Itinerary.
     *
     * @param itinerary The most recent Itinerary.
     */
    void setRecentItinerary(Itinerary itinerary);

    /**
     * Gets the most recent Itinerary.
     *
     * @return The most recent Itinerary.
     * @throws NoRecentItineraryException If there is no recent Itinerary.
     */
    Itinerary getRecentItinerary() throws NoRecentItineraryException;

    /**
     * Sets a new Itinerary.
     *
     * @param itinerary The new Itinerary.
     * @throws ParseException If there is an issue parsing.
     */
    void setNewItinerary(Itinerary itinerary) throws ParseException;

    /**
     * Confirms the most recent Itinerary.
     *
     * @param newName The new name of the itinerary.
     */
    void confirmRecentItinerary(String newName);

    /**
     * Returns profile of user.
     */
    ProfileCard getProfileCard();

    /**
     * Returns name of the user.
     */
    String getName();
}
