package duke.model;

import duke.data.BusService;
import duke.data.BusStop;
import duke.data.Location;
import duke.data.UniqueTaskList;
import duke.data.tasks.Task;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.util.List;

public interface Model {
    /**
     * Returns the list of tasks.
     */
    UniqueTaskList getTasks();

    /**
     * Returns the list of tasks that contains a date.
     */
    FilteredList<Task> getFilteredList();

    /**
     * Returns the list of tasks that is sorted chronologically.
     */
    SortedList<Task> getChronoSortedList();

    /**
     * Returns all the list of locations.
     */
    List<Location> getLocationList();

    /**
     * Returns the list of tasks that is a Holiday.
     */
    FilteredList<Task> getHolidayList();

    /**
     * Returns the list of all bus stops.
     */
    List<BusStop> getBusStops();

    /**
     * Returns the list of all bus routes.
     */
    List<BusService> getBusService();

    /**
     * Returns the list of all attractions.
     */
    List<Location> getRecommendations();

    /**
     * Saves the Model data in storage.
     */
    void save();
}
