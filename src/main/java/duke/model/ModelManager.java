package duke.model;

import duke.commons.exceptions.DukeException;
import duke.logic.CreateMap;
import duke.model.events.Event;
import duke.model.events.Task;
import duke.model.lists.EventList;
import duke.model.lists.TaskList;
import duke.model.lists.VenueList;
import duke.model.locations.BusStop;
import duke.model.locations.Venue;
import duke.model.transports.BusService;
import duke.storage.Storage;

import java.util.ArrayList;
import java.util.List;

public class ModelManager implements Model {
    private Storage storage;
    private TaskList tasks;
    private CreateMap map;
    //private List<Route> userRoutes;

    /**
     * Constructs a new ModelManager object.
     */
    public ModelManager() {
        storage = new Storage();
        tasks = storage.getTasks();
        map = storage.getMap();
        //userRoutes = storage.getRoutes();
    }

    @Override
    public CreateMap getMap() {
        return map;
    }

    @Override
    public TaskList getTasks() {
        return tasks;
    }

    @Override
    public List<Task> getFilteredList() {
        return tasks.getFilteredList();
    }

    @Override
    public List<Task> getChronoSortedList() {
        return tasks.getChronoList();
    }

    @Override
    public List<Venue> getLocationList() {
        //move this to UniqueTaskList
        List<Venue> locations = new ArrayList<>();
        for (Task t : tasks.getEventList()) {
            locations.add(((Event) t).getLocation());
        }
        return locations;
    }

    @Override
    public EventList getEventList() {
        return new EventList(tasks);
    }

    @Override
    public List<BusStop> getBusStops() {
        return null;
    }

    @Override
    public List<BusService> getBusService() {
        return null;
    }

    @Override
    public List<Venue> getRecommendations() throws DukeException {
        return storage.readVenues();
    }

    @Override
    public void save() throws DukeException {
        storage.write();
    }

    @Override
    public VenueList getEventVenues() {
        return new VenueList(tasks.getEventList());
    }
}
