package sgtravel.storage;

import sgtravel.commons.exceptions.DuplicateRouteException;
import sgtravel.commons.exceptions.DuplicateRouteNodeException;
import sgtravel.commons.exceptions.DuplicateTaskException;
import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.parsers.ParserTimeUtil;
import sgtravel.logic.parsers.storageparsers.EventStorageParser;
import sgtravel.logic.parsers.storageparsers.PlanningStorageParser;
import sgtravel.logic.parsers.storageparsers.TransportStorageParser;
import sgtravel.model.Event;
import sgtravel.model.lists.EventList;
import sgtravel.model.lists.RouteList;
import sgtravel.model.locations.Venue;
import sgtravel.model.planning.Agenda;
import sgtravel.model.planning.Itinerary;
import sgtravel.model.planning.Todo;
import sgtravel.model.transports.Route;

import java.time.LocalDateTime;
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

    private static final String EVENTS_FILE_PATH = "/sample/events.txt";
    private static final String ROUTES_FILE_PATH = "/sample/routes.txt";
    private static final String ITINERARIES_FILE_PATH = "/sample/itineraries.txt";

    /**
     * Reads all storage file.
     */
    public SampleStorage() {
        events = new EventList();
        routes = new RouteList();
        itineraryTable = new HashMap<>();
        readEvents();
        readRoutes();
        readItineraryTable();
    }

    /**
     * Reads the itinerary hash map from filepath.
     */
    private void readItineraryTable() {
        try {
            Scanner scanner = new Scanner(getClass().getResourceAsStream(ITINERARIES_FILE_PATH));
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine();
                LocalDateTime start = ParserTimeUtil.parseStringToDate(scanner.nextLine());
                LocalDateTime end = ParserTimeUtil.parseStringToDate(scanner.nextLine());
                Venue hotel = PlanningStorageParser.getVenueFromStorage(scanner.nextLine());
                Itinerary itinerary = new Itinerary(start, end, hotel, name);
                List<Agenda> agendaList = new ArrayList<>();
                String fileLine = scanner.nextLine();
                while (fileLine.split("\\|")[0].equals("Agenda ")) {
                    List<Venue> venueList = new ArrayList<>();
                    List<Todo> todoList;
                    final int number2 = Integer.parseInt(fileLine.split("\\|")[1]);
                    String newVenue = scanner.nextLine();
                    while (newVenue.contains(" | ")) {
                        venueList.add(PlanningStorageParser.getVenueFromStorage(newVenue));
                        newVenue = scanner.nextLine();
                    }
                    todoList = PlanningStorageParser.getTodoListFromStorage(newVenue);
                    Agenda agenda = new Agenda(todoList, venueList, number2);
                    agendaList.add(agenda);
                    if (scanner.hasNextLine()) {
                        fileLine = scanner.nextLine();
                    } else {
                        break;
                    }
                }
                itinerary.setTasks(agendaList);
                itineraryTable.put(itinerary.getName(), itinerary);
            }
            scanner.close();
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
                    newRoute.add(TransportStorageParser.createNodeFromStorage(input));
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

    public HashMap<String, Itinerary> getItineraryTable() {
        return this.itineraryTable;
    }
}
