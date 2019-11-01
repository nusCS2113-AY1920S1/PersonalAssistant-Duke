package duke;

import duke.commons.exceptions.DukeDateTimeParseException;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.FileLoadFailException;
import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.ItineraryInsufficientAgendasException;
import duke.model.transports.TransportationMap;
import duke.commons.exceptions.QueryFailedException;
import duke.commons.exceptions.RouteDuplicateException;
import duke.model.Model;
import duke.model.lists.EventList;
import duke.model.lists.RouteList;
import duke.model.lists.VenueList;
import duke.model.locations.BusStop;
import duke.model.planning.Agenda;
import duke.model.planning.Itinerary;
import duke.model.profile.ProfileCard;
import duke.model.transports.BusService;
import duke.model.transports.Route;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

public class ModelStub implements Model {
    private StorageStub storage;
    private EventList events;
    private RouteList routes;
    private TransportationMap map;
    private ProfileCard profileCard;
    private RouteManager routeManager;

    /**
     * Construct the ModelStub for testing.
     */
    public ModelStub() {
        storage = new StorageStub();
        events = new EventList();
        routes = new RouteList();
        map = storage.getMap();
        routeManager = new RouteManager(routes);
    }

    @Override
    public void setEvents(EventList events) {
        this.events = events;
    }

    @Override
    public EventList getEvents() {
        return events;
    }

    @Override
    public TransportationMap getMap() {
        return map;
    }

    @Override
    public RouteList getRoutes() {
        return routes;
    }

    @Override
    public void addRoute(Route route) throws RouteDuplicateException {
        routes.add(route);
    }

    @Override
    public void save() {
        System.out.println("");
    }

    @Override
    public EventList getSortedList() {
        return events.getSortedList();
    }

    @Override
    public HashMap<String, BusStop> getBusStops() {
        return map.getBusStopMap();
    }

    /**
     * Gets the bust stop from the map.
     *
     * @param query The bus stop to query.
     * @return The BusStop object.
     * @throws QueryFailedException If the bus stop cannot be found.
     */
    public BusStop getBusStop(String query) throws QueryFailedException {
        HashMap<String, BusStop> allBus = getMap().getBusStopMap();
        if (allBus.containsKey(query)) {
            return allBus.get(query);
        }

        throw new QueryFailedException("BUS_STOP");
    }

    @Override
    public List<BusService> getBusService() {
        return null;
    }

    @Override
    public List<Agenda> getRecommendations(int numDays, Itinerary itinerary) throws DukeException {

        List<Agenda> recommendations = storage.readVenues(numDays);
        itinerary.setTasks(recommendations);
        storage.writeItineraries(itinerary, 2);
        return recommendations;
    }

    @Override
    public VenueList getEventVenues() {
        return new VenueList();
    }

    @Override

    public Itinerary getItinerary(String name) throws DukeException {
        return storage.getItinerary(name);
    }

    @Override
    public void saveItinerary(Itinerary itinerary) throws FileNotSavedException, ItineraryInsufficientAgendasException {
        storage.writeItineraries(itinerary, 1);
    }

    @Override
    public String listItineraries() throws FileLoadFailException {
        return storage.readItineraryList();
    }

    @Override
    public void itineraryListSave(Itinerary itinerary) throws FileNotSavedException, FileNotFoundException {
        storage.writeItinerarySave(itinerary);
    }

    @Override
    public Itinerary readRecommendations() throws FileLoadFailException, DukeDateTimeParseException {
        return storage.readRecommendations();
    }

    public RouteManager getRouteManager() {
        return routeManager;
    }

    @Override
    public ProfileCard getProfileCard() {
        return profileCard;
    }

    @Override
    public boolean isNewUser() {
        return  storage.getIsNewUser();
    }

    @Override
    public String getName() {
        return profileCard.getPersonName();
    }
}
