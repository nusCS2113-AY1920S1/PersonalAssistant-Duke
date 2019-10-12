package duke.model;

import duke.commons.exceptions.DukeException;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.util.ArrayList;
import java.util.List;

public class ModelManager implements Model {
    UniqueTaskList tasks;
    List<Venue> recommendations;
    List<BusStop> allBusStops;
    //List<TrainStation> allTrainStations;

    @Override
    public UniqueTaskList getTasks() {
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
            try {
                locations.add(((Event) t).getLocation());
            } catch (DukeException e) {
                //silent failure
            }
        }
        return locations;
    }

    @Override
    public FilteredList<Task> getEventList() {
        return tasks.getEventList();
    }

    @Override
    public List<BusStop> getBusStops() {
        return allBusStops;
    }

    @Override
    public List<BusService> getBusService() {
        return null;
    }

    @Override
    public List<Venue> getRecommendations() {
        return recommendations;
    }

    @Override
    public void save() {

    }
}
