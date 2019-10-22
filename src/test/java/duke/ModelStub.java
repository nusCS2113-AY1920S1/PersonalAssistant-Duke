package duke;

import duke.commons.exceptions.DukeException;
import duke.logic.CreateMap;
import duke.model.Model;
import duke.model.lists.EventList;
import duke.model.lists.TaskList;
import duke.model.lists.VenueList;
import duke.model.events.Event;
import duke.model.events.Task;
import duke.model.locations.BusStop;
import duke.model.locations.Venue;
import duke.model.transports.BusService;
import duke.storage.Storage;

import java.util.ArrayList;
import java.util.List;

public class ModelStub implements Model {
    private Storage storage;
    private TaskList tasks;
    private CreateMap map;

    public ModelStub() {
        storage = new Storage();
        tasks = new TaskList();
    }

    @Override
    public TaskList getTasks() {
        return tasks;
    }

    @Override
    public CreateMap getMap() {
        return map;
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
    }

    @Override
    public VenueList getEventVenues() {
        return new VenueList();
    }
}
