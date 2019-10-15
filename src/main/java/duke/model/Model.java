package duke.model;

import duke.commons.exceptions.DukeException;
import duke.logic.CreateMap;
import duke.model.events.Task;
import duke.model.transports.BusService;
import duke.model.locations.BusStop;
import duke.model.locations.Venue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.util.List;

public interface Model {
    /**
     * Returns the list of tasks.
     */
    TaskList getTasks();

    /**
     * Return map object.
     */
    CreateMap getMap();

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
    List<Venue> getLocationList();

    /**
     * Returns the list of tasks that is an Event.
     */
    FilteredList<Task> getEventList();

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
    List<Venue> getRecommendations() throws DukeException;

    /**
     * Saves the Model data in storage.
     */
    void save() throws DukeException;
}
