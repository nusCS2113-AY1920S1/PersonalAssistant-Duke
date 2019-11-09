package sgtravel.model;

import sgtravel.commons.exceptions.AddListFailException;
import sgtravel.commons.exceptions.DuplicateRouteException;
import sgtravel.commons.exceptions.FileNotSavedException;
import sgtravel.commons.exceptions.NoRecentItineraryException;
import sgtravel.commons.exceptions.NoSuchItineraryException;
import sgtravel.commons.exceptions.OutOfBoundsException;
import sgtravel.model.lists.EventList;
import sgtravel.model.lists.RouteList;
import sgtravel.model.lists.VenueList;
import sgtravel.model.locations.BusStop;
import sgtravel.model.planning.Itinerary;
import sgtravel.model.planning.Recommendation;
import sgtravel.model.profile.ProfileCard;
import sgtravel.model.transports.Route;
import sgtravel.model.transports.TransportationMap;
import sgtravel.storage.Storage;

import java.util.HashMap;

/**
 * Implements the methods defined in the Model Interface.
 */
public class ModelManager implements Model {
    private Storage storage;
    private EventList events;
    private RouteList routes;
    private TransportationMap map;
    private ProfileCard profileCard;
    private Recommendation recommendations;
    private HashMap<String, Itinerary> itineraryTable;
    private Itinerary recentItinerary;

    /**
     * Constructs a new ModelManager object.
     */
    public ModelManager() {
        storage = new Storage();
        events = storage.getEvents();
        map = storage.getMap();
        routes = storage.getRoutes();
        profileCard = storage.getProfileCard();
        recommendations = storage.getRecommendations();
        itineraryTable = storage.getItineraryTable();
    }

    /**
     * Returns name of the user.
     */
    @Override
    public String getName() {
        return profileCard.getPersonName();
    }

    /**
     * Returns map object.
     */
    @Override
    public void addToFavourite(String name, Itinerary itinerary) throws NoSuchItineraryException {
        if (itinerary == null) {
            throw new NoSuchItineraryException();
        }
        profileCard.addFavourite(name, itinerary);
    }

    @Override
    public void deleteFavourite(String name) throws NoSuchItineraryException {
        profileCard.deleteFavourite(name);
    }

    @Override
    public TransportationMap getMap() {
        return map;
    }

    /**
     * Returns the list of events.
     */
    @Override
    public EventList getEvents() {
        return events;
    }

    /**
     * Replaces the events of this model with the new one.
     *
     * @param events The new events.
     */
    @Override
    public void setEvents(EventList events) {
        this.events = events;
    }

    /**
     * Returns the list of Routes.
     */
    @Override
    public RouteList getRoutes() {
        return routes;
    }

    /**
     * Gets a specified Route.
     *
     * @param index The index of the Route.
     */
    @Override
    public Route getRoute(int index) throws OutOfBoundsException {
        try {
            return routes.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new OutOfBoundsException();
        }
    }

    /**
     * Returns the map of all bus stops.
     */
    @Override
    public HashMap<String, BusStop> getBusStops() {
        return map.getBusStopMap();
    }

    /**
     * Returns the recommendation list object.
     *
     * @return recommendations The requested recommendations list.
     */
    @Override
    public Recommendation getRecommendations() {
        return recommendations;
    }

    /**
     * Returns the itinerary hash-map keyed by their names.
     *
     * @return itineraryTable The list of saved itineraries.
     */
    @Override
    public HashMap<String, Itinerary> getItineraryTable() {
        return itineraryTable;
    }

    /**
     * Deletes the requested itinerary from storage.
     *
     * @param name The name of the itinerary to be "done" (deleted).
     */
    @Override
    public void doneItinerary(String name) throws NoSuchItineraryException {
        if (itineraryTable.get(name) == null) {
            throw new NoSuchItineraryException();
        }
        this.itineraryTable.remove(name);
    }

    /**
     * Saves the most recent recommendation.
     *
     * @param recentItinerary The recent recommendation.
     */
    @Override
    public void setRecentItinerary(Itinerary recentItinerary) {
        this.recentItinerary = recentItinerary;
    }

    /**
     * Returns the "recently recommended itinerary".
     *
     * @return recentItinerary The recent recommendation.
     */
    @Override
    public Itinerary getRecentItinerary() throws NoRecentItineraryException {
        if (recentItinerary == null) {
            throw new NoRecentItineraryException();
        }
        return recentItinerary;
    }

    /**
     * Stores a new itinerary to storage.
     *
     * @param itinerary The itinerary to be saved.
     */
    @Override
    public void setNewItinerary(Itinerary itinerary) {
        this.itineraryTable.put(itinerary.getName(), itinerary);
    }

    /**
     * Stores the "recently recommended itinerary" into storage.
     *
     * @param name The new name for the itinerary list.
     */
    @Override
    public void confirmRecentItinerary(String name) throws AddListFailException {
        if ("".equals(name)) {
            throw new AddListFailException();
        }
        recentItinerary.setName(name);
        this.itineraryTable.put(name, recentItinerary);
    }

    /**
     * Gets the VenueList of events.
     *
     * @return The VenueList of events.
     */
    @Override
    public VenueList getEventVenues() {
        return new VenueList(events);
    }

    /**
     * Gets the ProfileCard.
     *
     * @return profileCard The ProfileCard.
     */
    @Override
    public ProfileCard getProfileCard() {
        return profileCard;
    }

    /**
     * Shows the Itinerary specified by a give name.
     *
     * @param name The serial number of the Itinerary.
     */
    @Override
    public Itinerary getItinerary(String name) {
        return itineraryTable.get(name);
    }

    /**
     * Adds a route to the list of routes.
     *
     * @param route The route to add.
     */
    @Override
    public void addRoute(Route route) throws DuplicateRouteException {
        routes.add(route);
    }

    /**
     * Saves the file to local storage.
     *
     * @throws FileNotSavedException If the file cannot be saved.
     */
    @Override
    public void save() throws FileNotSavedException {
        storage.write();
    }
}
