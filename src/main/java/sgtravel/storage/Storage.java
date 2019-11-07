package sgtravel.storage;


import sgtravel.commons.exceptions.DuplicateRouteException;
import sgtravel.commons.exceptions.DuplicateRouteNodeException;
import sgtravel.commons.exceptions.DuplicateTaskException;
import sgtravel.commons.exceptions.FileLoadFailException;
import sgtravel.commons.exceptions.FileNotSavedException;
import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.parsers.ParserTimeUtil;
import sgtravel.logic.parsers.storageparsers.EventStorageParser;
import sgtravel.logic.parsers.storageparsers.PlanningStorageParser;
import sgtravel.logic.parsers.storageparsers.ProfileStorageParser;
import sgtravel.logic.parsers.storageparsers.TransportStorageParser;
import sgtravel.model.Event;
import sgtravel.model.lists.EventList;
import sgtravel.model.lists.RouteList;
import sgtravel.model.locations.BusStop;
import sgtravel.model.locations.TrainStation;
import sgtravel.model.locations.Venue;
import sgtravel.model.planning.Agenda;
import sgtravel.model.planning.Itinerary;
import sgtravel.model.planning.Recommendation;
import sgtravel.model.planning.Todo;
import sgtravel.model.profile.ProfileCard;
import sgtravel.model.transports.BusService;
import sgtravel.model.transports.Route;
import sgtravel.model.transports.TransportationMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages storage of Duke data in local storage.
 */
public class Storage {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private EventList events;
    private RouteList routes;
    private TransportationMap map;
    private ProfileCard profileCard;
    private Recommendation recommendation;
    private HashMap<String, Itinerary> itineraryTable;

    private static final String BUS_FILE_PATH = "/data/bus.txt";
    private static final String RECOMMENDATIONS_FILE_PATH = "/data/recommendations.txt";
    private static final String TRAIN_FILE_PATH = "/data/train.txt";

    private static final String EVENTS_FILE_PATH = "events.txt";
    private static final String ROUTES_FILE_PATH = "routes.txt";
    private static final String ITINERARIES_FILE_PATH = "itineraries.txt";
    private static final String PROFILE_FILE_PATH = "profile.txt";

    /**
     * Constructs a Storage object that contains information from the model.
     */
    public Storage() {
        SampleStorage sampleData = new SampleStorage();
        events = sampleData.getEvents();
        routes = sampleData.getRoutes();
        itineraryTable = sampleData.getItineraryTable();
        try {
            read();
        } catch (FileLoadFailException | ParseException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    /**
     * Reads all storage file.
     */
    private void read() throws FileLoadFailException, ParseException {
        readBus();
        readTrain();
        readEvents();
        readRoutes();
        readProfile();
        readRecommendations();
        readItineraryTable();
    }

    /**
     * Reads train from filepath.
     */
    private void readTrain() {
        assert this.map != null : "Map must be created first";
        HashMap<String, TrainStation> trainMap = new HashMap<>();
        Scanner s = new Scanner(getClass().getResourceAsStream(TRAIN_FILE_PATH));
        while (s.hasNext()) {
            TrainStation newTrain = TransportStorageParser.createTrainFromStorage(s.nextLine());
            trainMap.put(newTrain.getDescription(), newTrain);
        }
        s.close();
        this.map.setTrainMap(trainMap);
    }

    /**
     * Reads bus from filepath.
     */
    private void readBus() {
        HashMap<String, BusStop> busStopData = new HashMap<>();
        HashMap<String, BusService> busData = new HashMap<>();
        Scanner s = new Scanner(getClass().getResourceAsStream(BUS_FILE_PATH));
        boolean isBusData = false;
        while (s.hasNext()) {
            String line = s.nextLine();
            if ("==========".equals(line)) {
                isBusData = true;
            }
            if (isBusData) {
                BusService busService = TransportStorageParser.createBusFromStorage(line);
                busData.put(busService.getBus(), busService);
            } else {
                BusStop busStop = TransportStorageParser.createBusStopDataFromStorage(line);
                busStopData.put(busStop.getBusCode(), busStop);
            }
        }
        s.close();
        this.map = new TransportationMap(busStopData, busData);
    }

    /**
     * Reads events from filepath. Creates empty events if file cannot be read.
     *
     * @throws FileLoadFailException If file cannot be loaded.
     */
    private void readEvents() throws FileLoadFailException {
        List<Event> events = new ArrayList<>();
        try {
            File f = new File(EVENTS_FILE_PATH);
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                events.add(EventStorageParser.createEventFromStorage(s.nextLine()));
            }
            s.close();
            this.events.setEvents(events);
        } catch (FileNotFoundException | ParseException | DuplicateTaskException e) {
            throw new FileLoadFailException(EVENTS_FILE_PATH);
        }
    }

    /**
     * Reads routes from filepath. Creates empty routes if file cannot be read.
     *
     * @throws FileLoadFailException If there is a duplicate route that is read.d.
     */
    private void readRoutes() throws FileLoadFailException {
        List<Route> newRoutes = new ArrayList<>();
        try {
            File file = new File(ROUTES_FILE_PATH);
            Scanner s = new Scanner(file);
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
        } catch (DuplicateRouteNodeException | FileNotFoundException
                | ParseException | DuplicateRouteException e) {
            throw new FileLoadFailException(ROUTES_FILE_PATH);
        }
    }

    /**
     * Reads the profile from filepath. Creates new empty profile if file doesn't exist.
     */
    private void readProfile() throws FileLoadFailException {
        profileCard = new ProfileCard();
        try {
            File f = new File(PROFILE_FILE_PATH);
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String input = s.nextLine();
                ProfileStorageParser.createProfileFromStorage(profileCard, input);
            }
            s.close();
        } catch (FileNotFoundException | ParseException e) {
            profileCard = new ProfileCard();
            throw new FileLoadFailException(PROFILE_FILE_PATH);
        }
    }

    /**
     * Returns Venues fetched from stored memory.
     */
    private void readRecommendations() throws ParseException {
        List<Agenda> agendaList = new ArrayList<>();
        Scanner scanner = new Scanner(getClass().getResourceAsStream(RECOMMENDATIONS_FILE_PATH));
        int i = 1;
        while (scanner.hasNext()) {
            List<Venue> venueList = new ArrayList<>();
            venueList.add(PlanningStorageParser.getVenueFromStorage(scanner.nextLine()));
            List<Todo> todoList = PlanningStorageParser.getTodoListFromStorage(scanner.nextLine());
            venueList.add(PlanningStorageParser.getVenueFromStorage(scanner.nextLine()));
            todoList.addAll(PlanningStorageParser.getTodoListFromStorage(scanner.nextLine()));
            Agenda agenda = new Agenda(todoList, venueList, i++);
            agendaList.add(agenda);
        }
        scanner.close();
        this.recommendation = new Recommendation(agendaList);
    }

    /**
     * Reads the itinerary hash map from storage.
     *
     * @throws FileLoadFailException If the file cannot be loaded.
     */
    private void readItineraryTable() throws FileLoadFailException {
        try {
            File itinerariesFile = new File(ITINERARIES_FILE_PATH);
            Scanner scanner = new Scanner(itinerariesFile);
            this.itineraryTable = makeItineraryTable(scanner);
        } catch (FileNotFoundException | ParseException e) {
            throw new FileLoadFailException(ITINERARIES_FILE_PATH);
        }
    }

    /**
     * Makes a hash-map containing all of the itineraries in storage.
     *
     * @throws ParseException If the file cannot be parsed correctly.
     */
    static HashMap<String, Itinerary> makeItineraryTable(Scanner scanner) throws ParseException {
        HashMap<String, Itinerary> itineraryTable = new HashMap<>();
        while (scanner.hasNextLine()) {
            String name = scanner.nextLine();
            LocalDateTime start = ParserTimeUtil.parseStringToDate(scanner.nextLine());
            LocalDateTime end = ParserTimeUtil.parseStringToDate(scanner.nextLine());
            Itinerary itinerary = new Itinerary(start, end, name);
            List<Agenda> agendaList = new ArrayList<>();
            String fileLine = scanner.nextLine();
            while (fileLine.split("\\|")[0].equals("Agenda ")) {
                Agenda agenda = getAgenda(scanner, fileLine);
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
        return itineraryTable;
    }

    /**
     * Makes an agenda object which is parsed from storage.
     *
     * @throws ParseException If the file cannot be parsed correctly.
     */
    private static Agenda getAgenda(Scanner scanner, String fileLine) throws ParseException {
        List<Venue> venueList = new ArrayList<>();
        List<Todo> todoList;
        final int number2 = Integer.parseInt(fileLine.split("\\|")[1]);
        String newVenue = scanner.nextLine();
        while (newVenue.contains(" | ")) {
            venueList.add(PlanningStorageParser.getVenueFromStorage(newVenue));
            newVenue = scanner.nextLine();
        }
        todoList = PlanningStorageParser.getTodoListFromStorage(newVenue);
        return new Agenda(todoList, venueList, number2);
    }

    /**
     * Writes the tasks into a file of the given filepath.
     *
     * @throws FileNotSavedException If a file cannot be saved.
     */
    public void write() throws FileNotSavedException {
        writeEvents();
        writeRoutes();
        writeProfile();
        writeNewItinerary();
    }

    /**
     * Writes the profile to local storage.
     *
     * @throws FileNotSavedException If the file cannot be saved.
     */
    private void writeProfile() throws FileNotSavedException {
        try {
            FileWriter writer = new FileWriter(PROFILE_FILE_PATH);
            writer.write(ProfileStorageParser.toProfileStorageString(profileCard) + "\n");
            writer.close();
        } catch (IOException e) {
            throw new FileNotSavedException(PROFILE_FILE_PATH);
        }
    }

    /**
     * Writes the events to local storage.
     *
     * @throws FileNotSavedException If the file cannot be saved.
     */
    private void writeEvents() throws FileNotSavedException {
        try {
            FileWriter writer = new FileWriter(EVENTS_FILE_PATH);
            for (Event event : events) {
                writer.write(EventStorageParser.toStorageString(event) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new FileNotSavedException(EVENTS_FILE_PATH);
        }
    }

    /**
     * Writes the events to local storage.
     *
     * @throws FileNotSavedException If the file cannot be saved.
     */
    private void writeRoutes() throws FileNotSavedException {
        try {
            FileWriter writer = new FileWriter(ROUTES_FILE_PATH);
            String routesString = "";
            for (Route route : routes) {
                routesString += TransportStorageParser.toRouteStorageString(route);
            }
            writer.write(routesString);
            writer.close();
        } catch (IOException e) {
            throw new FileNotSavedException(ROUTES_FILE_PATH);
        }
    }

    /**
     * Writes recommendations to filepath.
     *
     * @throws FileNotSavedException If the file cannot be saved.
     */
    private void writeNewItinerary() throws FileNotSavedException {
        String file = ITINERARIES_FILE_PATH;
        try {
            FileWriter writer = new FileWriter(file, false);
            for (Map.Entry<String,Itinerary> entry : itineraryTable.entrySet()) {
                writer.write(entry.getKey() + "\n" + entry.getValue().getStartDate().toString() + "\n"
                        + entry.getValue().getEndDate().toString() + "\n");
                for (Agenda agenda : entry.getValue().getList()) {
                    writer.write(agenda.toString());
                }
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new FileNotSavedException(file);
        }
    }

    public EventList getEvents() {
        return events;
    }

    public TransportationMap getMap() {
        return this.map;
    }

    public RouteList getRoutes() {
        return routes;
    }

    public ProfileCard getProfileCard() {
        return profileCard;
    }

    public Recommendation getRecommendations() {
        return recommendation;
    }

    public HashMap<String, Itinerary> getItineraryTable() {
        return this.itineraryTable;
    }
}
