package duke;

import duke.commons.exceptions.CorruptedFileException;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.QueryFailedException;
import duke.commons.exceptions.RouteDuplicateException;
import duke.logic.CreateMap;
import duke.model.Model;
import duke.model.lists.EventList;
import duke.model.lists.RouteList;
import duke.model.lists.VenueList;
import duke.model.Event;
import duke.model.Task;
import duke.model.locations.BusStop;
import duke.model.locations.Venue;
import duke.model.planning.Agenda;
import duke.model.planning.Itinerary;
import duke.model.transports.BusService;
import duke.model.transports.Route;
import duke.storage.Storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModelStub implements Model {
    private Storage storage;
    private EventList events;
    private RouteList routes;
    private CreateMap map;

    /**
     * Construct the ModelStub for testing.
     */
    public ModelStub() {
        storage = new Storage();
        events = new EventList();
        routes = new RouteList();
        map = storage.getMap();
    }

    @Override
    public EventList getEvents() {
        return events;
    }

    @Override
    public CreateMap getMap() {
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
    public void save() throws CorruptedFileException, FileNotSavedException {
        System.out.println("");
    }

    @Override
    public EventList getSortedList() {
        return events.getSortedList();
    }

    @Override
    public List<BusStop> getAllBusStops() {
        return null;
    }

    @Override
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
    public List<Agenda> getRecommendations(int numberOfDays, Itinerary itinerary) throws DukeException {
        return storage.readVenues(numberOfDays);
    }

    @Override
    public VenueList getEventVenues() {
        return new VenueList();
    }
}
