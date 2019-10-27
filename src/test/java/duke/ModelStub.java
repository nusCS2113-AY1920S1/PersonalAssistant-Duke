package duke;

import duke.commons.exceptions.CorruptedFileException;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.FileNotSavedException;
import duke.logic.CreateMap;
import duke.model.Model;
import duke.model.lists.EventList;
import duke.model.lists.RouteList;
import duke.model.lists.VenueList;
import duke.model.locations.BusStop;
import duke.model.planning.Agenda;
import duke.model.planning.Itinerary;
import duke.model.transports.BusService;
import duke.storage.Storage;

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
    public void save() throws CorruptedFileException, FileNotSavedException {
        System.out.println("");
    }

    @Override
    public EventList getSortedList() {
        return events.getSortedList();
    }

    @Override
    public HashMap<String, BusStop> getBusStops() {
        return null;
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
