package duke;

import duke.commons.exceptions.DukeDateTimeParseException;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.FileLoadFailException;
import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.ItineraryInsufficientAgendasException;
import duke.commons.exceptions.ParseException;
import duke.commons.exceptions.RecommendationDayExceededException;
import duke.model.Model;
import duke.model.planning.Recommendation;
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
public class ModelStub implements Model {
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
    public ModelStub() throws FileLoadFailException {
        storage = new Storage();
        events = storage.getEvents();
        map = storage.getMap();
        routes = storage.getRoutes();
        profileCard = storage.getProfileCard();
        recommendations = storage.getRecommendations();
        itineraryTable = storage.readItineraryTable();
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
    public Recommendation getRecommendations() {
        return recommendations;
    }

    @Override
    public HashMap<String, Itinerary> getItineraryTable() {
        return itineraryTable;
    }

    @Override
    public void storeRecentItinerary(Itinerary recentItinerary) {
        this.recentItinerary = recentItinerary;
    }

    @Override
    public Itinerary getRecentItinerary() {
        return recentItinerary;
    }

    @Override
    public void storeInItineraryTable (Itinerary itinerary , String[] itineraryDetails) throws ParseException {
        storage.storeNewItinerary(itinerary, itineraryDetails);
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
     * Shows the Itinerary specified by a give serial number.
     *
     * @param name The serial number of the Itinerary.
     */
    @Override
    public Itinerary getItinerary(String name) {
        return storage.getItinerary(name);
//      return itineraryTable.get(name);
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
