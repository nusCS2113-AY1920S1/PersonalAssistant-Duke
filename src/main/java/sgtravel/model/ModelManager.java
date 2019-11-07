package sgtravel.model;

import sgtravel.commons.exceptions.FileNotSavedException;
import sgtravel.commons.exceptions.NoRecentItineraryException;
import sgtravel.commons.exceptions.DuplicateRouteException;
import sgtravel.commons.exceptions.OutOfBoundsException;
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
import sgtravel.storage.Storage;

import java.util.HashMap;
import java.util.List;

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

    @Override
    public String getName() {
        return profileCard.getPersonName();
    }

    @Override
    public void doneItinerary(String name) throws OutOfBoundsException {
        if(itineraryTable.get(name) == null) {
            throw new OutOfBoundsException();
        }
        this.itineraryTable.remove(name);
    }

    @Override
    public TransportationMap getMap() {
        return map;
    }

    @Override
    public EventList getEvents() {
        return events;
    }

    @Override
    public void setEvents(EventList events) {
        this.events = events;
    }

    @Override
    public RouteList getRoutes() {
        return routes;
    }

    @Override
    public Route getRoute(int index) throws OutOfBoundsException {
        try {
            return routes.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new OutOfBoundsException();
        }
    }

    @Override
    public EventList getSortedList() {
        return events.getSortedList();
    }

    @Override
    public HashMap<String, BusStop> getBusStops() {
        return map.getBusStopMap();
    }

    @Override
    public List<BusService> getBusService() {
        return null;
    }

    @Override
    public Recommendation getRecommendations() {
        return recommendations;
    }

    @Override
    public HashMap<String, Itinerary> getItineraryTable() {
        return itineraryTable;
    }

    @Override
    public void setRecentItinerary(Itinerary recentItinerary) {
        this.recentItinerary = recentItinerary;
    }

    @Override
    public Itinerary getRecentItinerary() throws NoRecentItineraryException {
        if (recentItinerary == null) {
            throw new NoRecentItineraryException();
        }
        return recentItinerary;
    }

    @Override
    public void setNewItinerary(Itinerary itinerary) {
        this.itineraryTable.put(itinerary.getName(), itinerary);
    }

    @Override
    public void confirmRecentItinerary(String name) {
        recentItinerary.setName(name);
        this.itineraryTable.put(name, recentItinerary);
    }

    @Override
    public VenueList getEventVenues() {
        return new VenueList(events);
    }

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
