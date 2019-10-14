package duke;

import duke.commons.exceptions.DukeException;
import duke.model.Model;
import duke.model.TaskList;
import duke.model.events.Event;
import duke.model.events.Task;
import duke.model.locations.BusStop;
import duke.model.locations.Venue;
import duke.model.transports.BusService;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.util.ArrayList;
import java.util.List;

public class ModelStub implements Model {
    private TaskList tasks;

    public ModelStub() {
        tasks = new TaskList();
    }

    @Override
    public TaskList getTasks() {
        return tasks;
    }

    @Override
    public FilteredList<Task> getFilteredList() {
        return tasks.getFilteredList();
    }

    @Override
    public SortedList<Task> getChronoSortedList() {
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
    public FilteredList<Task> getEventList() {
        return tasks.getEventList();
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
    public List<Venue> getRecommendations() {
        List<Venue> recommendations = new ArrayList<>();
        recommendations.add(new Venue("Sentosa", 1.2454983, 103.8437327, 0, 0));
        recommendations.add(new Venue("Mandai", 1.421336, 103.802622, 0, 0));
        recommendations.add(new Venue("lck", 1.431321, 103.718253, 0, 0));
        recommendations.add(new Venue("Jurong island", 1.254945,
                103.678820, 0, 0));
        recommendations.add(new Venue("Changi Airport", 1.346703,
                103.986755, 0, 0));
        recommendations.add(new Venue("Bukit Timah", 1.327360,
                103.794509, 0, 0));
        return recommendations;
    }

    @Override
    public void save() throws DukeException {
    }
}
