package duke.model;

import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.FileNotSavedException;
import duke.logic.TransportationMap;
import duke.model.lists.EventList;
import duke.model.lists.RouteList;
import duke.model.lists.VenueList;
import duke.model.locations.BusStop;
import duke.model.planning.Agenda;
import duke.model.planning.Itinerary;
import duke.model.profile.ProfileCard;
import duke.model.transports.BusService;
import duke.storage.Storage;

import java.util.List;

/**
 * Implements the methods defined in the Model Interface.
 */
public class ModelManager implements Model {
    private Storage storage;
    private EventList events;
    private RouteList routes;
    private TransportationMap map;
    private ProfileCard profileCard;

    /**
     * Constructs a new ModelManager object.
     */
    public ModelManager() {
        storage = new Storage();
        events = storage.getEvents();
        map = storage.getMap();
        routes = storage.getRoutes();
        profileCard = storage.getProfileCard();
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
    public RouteList getRoutes() {
        return routes;
    }

    @Override
    public EventList getSortedList() {
        return events.getSortedList();
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
    public List<Agenda> getRecommendations(int numDays, Itinerary itinerary) throws DukeException {
        List<Agenda> recommendations = storage.readVenues(numDays);
        itinerary.setTasks(recommendations);
        storage.writeRecommendations(itinerary);
        return recommendations;
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
     * Saves the file to local storage.
     *
     * @throws FileNotSavedException If the file cannot be saved.
     */
    @Override
    public void save() throws FileNotSavedException {
        storage.write();
    }

    @Override
    public boolean isNewUser() {
        return storage.getIsNewUser();
    }
}
