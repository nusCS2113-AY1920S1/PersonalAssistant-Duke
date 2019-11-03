package duke.storage;

import duke.commons.Messages;
import duke.commons.exceptions.ApiException;
import duke.commons.exceptions.CategoryNotFoundException;
import duke.commons.exceptions.CorruptedFileException;
import duke.commons.exceptions.DukeDuplicateTaskException;
import duke.commons.exceptions.FileLoadFailException;
import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.ParseException;
import duke.commons.exceptions.RouteDuplicateException;
import duke.commons.exceptions.RouteNodeDuplicateException;
import duke.commons.exceptions.StartEndDateBeforeNowException;
import duke.commons.exceptions.StartEndDateDiscordException;
import duke.logic.api.ApiParser;
import duke.logic.parsers.ParserStorageUtil;
import duke.logic.parsers.ParserTimeUtil;
import duke.model.Event;
import duke.model.lists.AgendaList;
import duke.model.lists.EventList;
import duke.model.lists.RouteList;
import duke.model.locations.BusStop;
import duke.model.locations.TrainStation;
import duke.model.locations.Venue;
import duke.model.planning.Agenda;
import duke.model.planning.Itinerary;
import duke.model.planning.Recommendation;
import duke.model.planning.Todo;
import duke.model.profile.ProfileCard;
import duke.model.transports.BusService;
import duke.model.transports.Route;
import duke.model.transports.TransportationMap;

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
public class StorageStub {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private EventList events;
    private RouteList routes;
    private TransportationMap map;
    private ProfileCard profileCard;
    private Recommendation recommendation;
    private HashMap<String, Itinerary> itineraryTable;
    private Itinerary newItinerary;

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
    public StorageStub() throws FileLoadFailException {
        events = new EventList();
        routes = new RouteList();
        read();
    }

    /**
     * Reads all storage file.
     */
    private void read() throws FileLoadFailException {
        readBus();
        readTrain();
        readRecommendations();
        readItineraryTable();
    }

    /**
     * Reads the itinerary hash map from storage.
     *
     * @throws FileLoadFailException If the file cannot be loaded.
     */
    public void readItineraryTable() throws FileLoadFailException {
        itineraryTable = new HashMap<>();
        try {
            File itinerariesFile = new File(ITINERARIES_FILE_PATH);
            Scanner scanner = new Scanner(itinerariesFile);
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine();
                LocalDateTime start = ParserTimeUtil.parseStringToDate(scanner.nextLine());
                LocalDateTime end = ParserTimeUtil.parseStringToDate(scanner.nextLine());
                Venue hotel = ParserStorageUtil.getVenueFromStorage(scanner.nextLine());
                Itinerary itinerary = new Itinerary(start, end, hotel, name);
                List<Agenda> agendaList = new ArrayList<>();
                String fileLine = scanner.nextLine();
                while (fileLine.split("\\|")[0].equals("Agenda ")) {
                    List<Venue> venueList = new ArrayList<>();
                    List<Todo> todoList;
                    final int number2 = Integer.parseInt(fileLine.split("\\|")[1]);
                    String newVenue = scanner.nextLine();
                    while (newVenue.contains(" | ")) {
                        venueList.add(ParserStorageUtil.getVenueFromStorage(newVenue));
                        newVenue = scanner.nextLine();
                    }
                    todoList = ParserStorageUtil.getTodoListFromStorage(newVenue);
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
        } catch (FileNotFoundException | ParseException e) {
            itineraryTable = new HashMap<>();
            logger.info(e.getMessage());
            throw new FileLoadFailException(ITINERARIES_FILE_PATH);
        }
    }

    /**
     * Reads train from filepath.
     */
    private void readTrain() {
        assert this.map != null : "Map must be created first";
        HashMap<String, TrainStation> trainMap = new HashMap<>();
        Scanner s = new Scanner(getClass().getResourceAsStream(TRAIN_FILE_PATH));
        while (s.hasNext()) {
            TrainStation newTrain = ParserStorageUtil.createTrainFromStorage(s.nextLine());
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
                BusService busService = ParserStorageUtil.createBusFromStorage(line);
                busData.put(busService.getBus(), busService);
            } else {
                BusStop busStop = ParserStorageUtil.createBusStopDataFromStorage(line);
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
    private void readEvent() throws FileLoadFailException {
        List<Event> events = new ArrayList<>();
        try {
            File f = new File(EVENTS_FILE_PATH);
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                events.add(ParserStorageUtil.createTaskFromStorage(s.nextLine()));
            }
            s.close();
            this.events.setEvents(events);
        } catch (FileNotFoundException | ParseException | DukeDuplicateTaskException e) {
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
                    newRoute = ParserStorageUtil.createRouteFromStorage(input);
                } else {
                    newRoute.add(ParserStorageUtil.createNodeFromStorage(input));
                }
            }
            if (!newRoute.getName().equals("")) {
                newRoutes.add(newRoute);
            }
            s.close();
            routes.setRoutes(newRoutes);
        } catch (FileNotFoundException | RouteDuplicateException | CorruptedFileException
                | RouteNodeDuplicateException e) {
            throw new FileLoadFailException(ROUTES_FILE_PATH);
        }
    }

    /**
     * Returns Venues fetched from stored memory.
     *
     */
    public void readRecommendations() {
        List<Agenda> agendaList = new ArrayList<>();
        Scanner scanner = new Scanner(getClass().getResourceAsStream(RECOMMENDATIONS_FILE_PATH));
        int i = 1;
        while (scanner.hasNext()) {
            List<Venue> venueList = new ArrayList<>();
            venueList.add(ParserStorageUtil.getVenueFromStorage(scanner.nextLine()));
            List<Todo> todoList = ParserStorageUtil.getTodoListFromStorage(scanner.nextLine());
            venueList.add(ParserStorageUtil.getVenueFromStorage(scanner.nextLine()));
            todoList.addAll(ParserStorageUtil.getTodoListFromStorage(scanner.nextLine()));
            Agenda agenda = new Agenda(todoList, venueList, i++);
            agendaList.add(agenda);
        }
        scanner.close();
        this.recommendation = new Recommendation(agendaList);
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
                ParserStorageUtil.createProfileFromStorage(profileCard, input);
            }
            s.close();
        } catch (FileNotFoundException | ParseException | CategoryNotFoundException e) {
            profileCard = new ProfileCard();
            throw new FileLoadFailException(PROFILE_FILE_PATH);
        }
    }

    /**
     * Writes the tasks into a file of the given filepath.
     *
     * @throws FileNotSavedException If a file cannot be saved.
     */
    public void write() throws FileNotSavedException {
        writeEvents();
        writeRoutes();
    }

    /**
     * Writes the profile to local storage.
     *
     * @throws FileNotSavedException If the file cannot be saved.
     */
    private void writeProfile() throws FileNotSavedException {
        try {
            FileWriter writer = new FileWriter(PROFILE_FILE_PATH);
            writer.write(ParserStorageUtil.toProfileStorageString(profileCard) + "\n");
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
                writer.write(ParserStorageUtil.toStorageString(event) + "\n");
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
                routesString += ParserStorageUtil.toRouteStorageString(route);
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
    public void writeNewItinerary() throws FileNotSavedException {
        String file = ITINERARIES_FILE_PATH;
        try {
            if (newItinerary != null) {
                FileWriter writer = new FileWriter(file, true);
                writer.write(newItinerary.getName() + "\n" + newItinerary.getStartDate().toString() + "\n"
                        + newItinerary.getEndDate().toString() + "\n"
                        + newItinerary.getHotelLocation().toString() + "\n");
                for (Agenda agenda : newItinerary.getList()) {
                    writer.write(agenda.toString());
                }
                writer.write("\n");
                writer.close();
            }
        } catch (IOException e) {
            throw new FileNotSavedException(file);
        }
    }

    /**
     * Retrieves an itinerary from persistent storage based on its serial number.
     *
     * @param name The itineraries serial number.
     */
    public Itinerary getItinerary(String name) {
        return itineraryTable.get(name);
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

    /**
     * Stores a newly added itinerary.
     *
     * @throws ParseException If itinerary is not parsed properly.
     */
    public void storeNewItinerary(Itinerary itinerary, String[] itineraryDetails) throws ParseException {
        List<Agenda> agendaList = new ArrayList<>();
        int i = 4;
        try {
            while (i < itineraryDetails.length) {
                List<Venue> venueList = new ArrayList<>();
                List<Todo> todoList = new ArrayList<>();
                final int number = Integer.parseInt(itineraryDetails[i++]);
                while (itineraryDetails[i].equals("/venue")) {
                    i++;
                    venueList.add(ApiParser.getLocationSearch(itineraryDetails[i++]));
                    StringBuilder todos = new StringBuilder();
                    if (i == itineraryDetails.length - 1 || itineraryDetails[i].matches("-?\\d+")) {
                        throw new ParseException(Messages.ITINERARY_EMPTY_TODOLIST);
                    }
                    todos.append(itineraryDetails[++i]).append("|");
                    i++;
                    while (itineraryDetails[i].equals("/and")) {
                        i++;
                        todos.append(itineraryDetails[i++]).append("|");
                        if (i >= itineraryDetails.length) {
                            break;
                        }
                    }
                    todoList = ParserStorageUtil.getTodoListFromStorage(todos.toString());
                    if (i >= itineraryDetails.length) {
                        break;
                    }
                }
                Agenda agenda = new Agenda(todoList, venueList, number);
                agendaList.add(agenda);
                itinerary.setTasks(agendaList);

                this.itineraryTable.put(itinerary.getName(), itinerary);
                this.newItinerary = itinerary;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParseException(Messages.ITINERARY_FAIL_CREATION);
        } catch (NumberFormatException e) {
            throw new ParseException(Messages.ITINERARY_INCORRECT_COMMAND);
        } catch (ApiException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void confirmRecentItinerary(Itinerary recentItinerary) {
        this.itineraryTable.put(recentItinerary.getName(), recentItinerary);
        this.newItinerary = recentItinerary;
    }

    public HashMap<String, Itinerary> getItineraryTable() {
        return this.itineraryTable;
    }
}