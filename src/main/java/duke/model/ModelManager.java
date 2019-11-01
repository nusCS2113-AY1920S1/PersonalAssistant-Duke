package duke.model;

import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.FileLoadFailException;
import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.ItineraryInsufficientAgendasException;
import duke.commons.exceptions.ParseException;
import duke.model.transports.TransportationMap;
import duke.commons.exceptions.RouteDuplicateException;
import duke.model.lists.EventList;
import duke.model.lists.RouteList;
import duke.model.lists.VenueList;
import duke.model.locations.BusStop;
import duke.model.planning.Agenda;
import duke.model.planning.Itinerary;
import duke.model.profile.ProfileCard;
import duke.model.transports.BusService;
import duke.model.transports.Route;
import duke.storage.Storage;

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

    /**
     * Constructs a new ModelManager object.
     */
    public ModelManager() {
        storage = new Storage();
        events = storage.getEvents();
        map = storage.getMap();
        routes = storage.getRoutes();
        profileCard = storage.getProfileCard();
    }

    @Override
    public String getName() {
        return profileCard.getPersonName();
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
    public List<Agenda> getRecommendations(int numDays, Itinerary itinerary) throws FileLoadFailException,
            FileNotSavedException {

        List<Agenda> recommendations = storage.readVenues(numDays);
        itinerary.setTasks(recommendations);
        storage.writeItineraries(itinerary, 2);
        return recommendations;
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
     * Save a newly created Itinerary to storage.
     *
     * @param itinerary The itinerary to be stored.
     */
    @Override
    public void saveItinerary(Itinerary itinerary)  throws FileNotSavedException {
        storage.writeItineraries(itinerary, 1);
    }

    /**
     * Save the newly created itinerary to the Itinerary Lists table of contents.
     *
     * @param itinerary The itinerary to be stored.
     */
    @Override
    public void itineraryListSave(Itinerary itinerary) throws FileNotSavedException {
        storage.writeItinerarySave(itinerary);
    }

    /**
     * Shows the Stored Itineraries Table of Contents.
     */
    @Override
    public String listItineraries() throws FileLoadFailException {
        return storage.readItineraryList();
    }

    /**
     * Shows the Itinerary specified by a give serial number.
     *
     * @param number The serial number of the Itinerary.
     */
    @Override
    public Itinerary getItinerary(String number) throws FileLoadFailException {
        return storage.getItinerary(number);
    }

    @Override
    public Itinerary readRecommendations() throws FileLoadFailException {
        return storage.readRecommendations();
    }

    /**
     * Adds a route to the list of routes.
     *
     * @param route The route to add.
     */
    @Override
    public void addRoute(Route route) throws RouteDuplicateException {
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
