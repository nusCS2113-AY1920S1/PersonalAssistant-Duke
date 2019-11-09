package sgtravel.storage;

import sgtravel.commons.exceptions.DuplicateRouteException;
import sgtravel.commons.exceptions.DuplicateRouteNodeException;
import sgtravel.commons.exceptions.DuplicateTaskException;
import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.parsers.storageparsers.EventStorageParser;
import sgtravel.logic.parsers.storageparsers.TransportStorageParser;
import sgtravel.model.Event;
import sgtravel.model.lists.EventList;
import sgtravel.model.lists.RouteList;
import sgtravel.model.planning.Itinerary;
import sgtravel.model.profile.ProfileCard;
import sgtravel.model.transports.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Helper class to generate sample data for first time user.
 */
public class SampleStorage {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private EventList events;
    private RouteList routes;
    private HashMap<String, Itinerary> itineraryTable;
    private ProfileCard profileCard;

    private static final String EVENTS_FILE_PATH = "/sample/events.txt";
    private static final String ROUTES_FILE_PATH = "/sample/routes.txt";
    private static final String ITINERARIES_FILE_PATH = "/sample/itineraries.txt";
    private static final String FAVOURITE_FILE_PATH = "/sample/favourite.txt";

    /**
     * Reads all storage file.
     */
    public SampleStorage() {
        events = new EventList();
        routes = new RouteList();
        itineraryTable = new HashMap<>();
        profileCard = new ProfileCard();
        readFavourite();
        readEvents();
        readRoutes();
        readItineraryTable();
    }

    /**
     * Reads the itinerary hash map from filepath.
     */
    private void readFavourite() {
        try {
            Scanner scanner = new Scanner(getClass().getResourceAsStream(FAVOURITE_FILE_PATH));
            this.profileCard.setFavourite(Storage.makeItineraryTable(scanner));
        } catch (ParseException e) {
            logger.log(Level.INFO, "Sample data not found.");
        }
    }

    /**
     * Reads the itinerary hash map from filepath.
     */
    private void readItineraryTable() {
        try {
            Scanner scanner = new Scanner(getClass().getResourceAsStream(ITINERARIES_FILE_PATH));
            this.itineraryTable = Storage.makeItineraryTable(scanner);
        } catch (ParseException e) {
            logger.log(Level.INFO, "Sample data not found.");
        }
    }

    /**
     * Reads events from filepath.
     */
    private void readEvents() {
        List<Event> events = new ArrayList<>();
        try {
            Scanner s = new Scanner(getClass().getResourceAsStream(EVENTS_FILE_PATH));
            while (s.hasNext()) {
                events.add(EventStorageParser.createEventFromStorage(s.nextLine()));
            }
            s.close();
            this.events.setEvents(events);
        } catch (ParseException | DuplicateTaskException e) {
            logger.log(Level.INFO, "Sample data not found.");
        }
    }

    /**
     * Reads routes from filepath.
     */
    private void readRoutes() {
        List<Route> newRoutes = new ArrayList<>();
        try {
            Scanner s = new Scanner(getClass().getResourceAsStream(ROUTES_FILE_PATH));
            Route newRoute = new Route(new ArrayList<>(), "", "");
            while (s.hasNext()) {
                String input = s.nextLine();
                if (input.split("\\|", 2)[0].strip().equals("route")) {
                    if (newRoute.size() != 0) {
                        newRoutes.add(newRoute);
                    }
                    newRoute = TransportStorageParser.createRouteFromStorage(input);
                } else {
                    newRoute.addNode(TransportStorageParser.createNodeFromStorage(input));
                }
            }
            if (!newRoute.getName().equals("")) {
                newRoutes.add(newRoute);
            }
            s.close();
            routes.setRoutes(newRoutes);
        } catch (DuplicateRouteNodeException | ParseException | DuplicateRouteException e) {
            logger.log(Level.INFO, "Sample data not found.");
        }
    }

    public EventList getEvents() {
        return events;
    }

    public RouteList getRoutes() {
        return routes;
    }

    public ProfileCard getProfileCard() {
        return profileCard;
    }

    /**
     * Returns the itinerary hash-map.
     */
    public HashMap<String, Itinerary> getItineraryTable() {
        return this.itineraryTable;
    }
}
