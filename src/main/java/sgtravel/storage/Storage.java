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
    private static final String FAVOURITE_FILE_PATH = "favourite.txt";

    /**
     * Constructs a Storage object that contains information from the model.
     */
    public Storage() {
        SampleStorage sampleData = new SampleStorage();
        profileCard = sampleData.getProfileCard();
        events = sampleData.getEvents();
        routes = sampleData.getRoutes();
        itineraryTable = sampleData.getItineraryTable();
        try {
            read();
        } catch (FileLoadFailException | ParseException | FileNotSavedException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    /**
     * Reads all storage file.
     */
    private void read() throws FileLoadFailException, ParseException, FileNotSavedException {
        readBus();
        readTrain();
        readRecommendations();
        readEvents();
        readRoutes();
        readProfile();
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
     * @throws FileLoadFailException If events file cannot be loaded.
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
                    newRoute.addNode(TransportStorageParser.createNodeFromStorage(input));
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
     *
     * @throws FileLoadFailException If the profile file is not found.
     */
    private void readProfile() throws FileLoadFailException, FileNotSavedException {
        try {
            File f = new File(PROFILE_FILE_PATH);
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String input = s.nextLine();
                ProfileStorageParser.createProfileFromStorage(profileCard, input);
            }
        } catch (FileNotFoundException | ParseException e) {
            throw new FileLoadFailException(PROFILE_FILE_PATH);
        }
        readFavouriteList();
    }

    /**
     * Reads favourite list from favourite.txt.
     *
     * @throws FileNotSavedException If file does not exist.
     */
    private void readFavouriteList() throws FileNotSavedException {
        try {
            File itinerariesFile = new File(FAVOURITE_FILE_PATH);
            Scanner scanner = new Scanner(itinerariesFile);
            HashMap<String, Itinerary> itinerary = makeItineraryTable(scanner);
            profileCard.setFavourite(itinerary);
        } catch (FileNotFoundException | ParseException e) {
            writeFavItinerary();
        }
    }

    /**
     * Reads recommendations from storage and creates a new recommendation object.
     *
     * @throws ParseException If the venue cannot be retrieved from storage.
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
     * Sets the itinerary hash-map in the Storage class.
     *
     * @throws FileLoadFailException If the itneraries file cannot be loaded.
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
        writeFavItinerary();
        writeNewItinerary();
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
            StringBuilder routesString = new StringBuilder();
            for (Route route : routes) {
                routesString.append(TransportStorageParser.toRouteStorageString(route));
            }
            writer.write(routesString.toString());
            writer.close();
        } catch (IOException e) {
            throw new FileNotSavedException(ROUTES_FILE_PATH);
        }
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
     * Writes favourite itineraries to indicated filepath.
     *
     * @throws FileNotSavedException If the file cannot be saved.
     */
    private void writeFavItinerary() throws FileNotSavedException {
        try {
            saveEntireItineraryList(FAVOURITE_FILE_PATH, profileCard.getFavouriteList());
        } catch (IOException e) {
            throw new FileNotSavedException(FAVOURITE_FILE_PATH);
        }
    }

    /**
     * Writes itineraries to indicated filepath.
     *
     * @throws FileNotSavedException If the file cannot be saved.
     */
    private void writeNewItinerary() throws FileNotSavedException {
        try {
            saveEntireItineraryList(ITINERARIES_FILE_PATH, itineraryTable);
        } catch (IOException e) {
            throw new FileNotSavedException(ITINERARIES_FILE_PATH);
        }
    }

    /**
     * Performs the writing of an itineraryTable or favoriteList to specified filepath.
     *
     * @throws FileNotSavedException If the file cannot be saved.
     */
    private void saveEntireItineraryList(String file, HashMap<String, Itinerary> itineraryTable) throws IOException {
        FileWriter writer = new FileWriter(file, false);
        StringBuilder itineraryString = new StringBuilder();
        for (Map.Entry<String, Itinerary> entry : itineraryTable.entrySet()) {
            itineraryString.append(PlanningStorageParser.toItineraryStorageString(entry.getValue()));
        }
        writer.write(itineraryString.toString());
        writer.close();
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

    /**
     * Returns the recommendations object created by the recommendations command.
     */
    public Recommendation getRecommendations() {
        return recommendation;
    }

    /**
     * Returns the itinerary hash-map.
     */
    public HashMap<String, Itinerary> getItineraryTable() {
        return this.itineraryTable;
    }
}
