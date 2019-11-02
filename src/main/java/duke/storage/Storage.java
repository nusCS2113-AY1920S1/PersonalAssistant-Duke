package duke.storage;

import duke.commons.exceptions.CategoryNotFoundException;
import duke.commons.exceptions.CorruptedFileException;
import duke.commons.exceptions.DukeDuplicateTaskException;
import duke.commons.exceptions.FileLoadFailException;
import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.ParseException;
import duke.commons.exceptions.RouteDuplicateException;
import duke.commons.exceptions.RouteNodeDuplicateException;
import duke.model.transports.TransportationMap;
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
import duke.model.planning.Todo;
import duke.model.profile.ProfileCard;
import duke.model.transports.BusService;
import duke.model.transports.Route;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private static final String BUS_FILE_PATH = "/data/bus.txt";
    private static final String RECOMMENDATIONS_FILE_PATH = "/data/recommendations.txt";
    private static final String TRAIN_FILE_PATH = "/data/train.txt";

    private static final String EVENTS_FILE_PATH = "events.txt";
    private static final String ROUTES_FILE_PATH = "routes.txt";
    private static final String ITINERARIES_FILE_PATH = "itineraries.txt";
    private static final String SAMPLE_RECOMMENDATIONS_FILE_PATH = "samples.txt";
    private static final String ITINERARY_LIST_FILE_PATH = "itineraryTable.txt";
    private static final String PROFILE_FILE_PATH = "profile.txt";

    /**
     * Constructs a Storage object that contains information from the model.
     */
    public Storage() {
        events = new EventList();
        routes = new RouteList();
        try {
            read();
        } catch (FileLoadFailException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    /**
     * Reads all storage file.
     */
    private void read() throws FileLoadFailException {
        readBus();
        readTrain();
        readProfile();
        readEvent();
        readRoutes();
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
                    newRoute.addNode(ParserStorageUtil.createNodeFromStorage(input));
                }
            }
            if (!newRoute.getName().equals("")) {
                newRoutes.add(newRoute);
            }
            s.close();
            routes.setRoutes(newRoutes);
        } catch (FileNotFoundException | RouteDuplicateException | CorruptedFileException |
                RouteNodeDuplicateException e) {
            throw new FileLoadFailException(ROUTES_FILE_PATH);
        }
    }

    /**
     * Returns Venues fetched from stored memory.
     *
     * @return The List of all Venues in Recommendations list.
     */
    public List<Agenda> readVenues(int numDays) throws FileLoadFailException {
        List<Agenda> recommendations = new ArrayList<>();
        Scanner scanner = new Scanner(getClass().getResourceAsStream(RECOMMENDATIONS_FILE_PATH));
        int i = 1;
        if (numDays > 8) {
            throw new FileLoadFailException("Don't throw a logic exception here. "
                    + "Storage is for Storage only. Change this.");
        }
        while (scanner.hasNext() && i <= numDays) {
            List<Venue> venueList = new ArrayList<>();
            venueList.add(ParserStorageUtil.getVenueFromStorage(scanner.nextLine()));
            List<Todo> todoList = ParserStorageUtil.getTodoListFromStorage(scanner.nextLine());
            venueList.add(ParserStorageUtil.getVenueFromStorage(scanner.nextLine()));
            todoList.addAll(ParserStorageUtil.getTodoListFromStorage(scanner.nextLine()));
            Agenda agenda = new Agenda(todoList, venueList, i++);
            recommendations.add(agenda);
        }
        scanner.close();
        return recommendations;
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
        writeProfile();
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
    public void writeItineraries(Itinerary itinerary, int type) throws FileNotSavedException {
        String file;
        if (type == 1) {
            file = ITINERARIES_FILE_PATH;
        } else {
            file = SAMPLE_RECOMMENDATIONS_FILE_PATH;
        }
        try {
            FileWriter writer = new FileWriter(file, true);
            writer.write(itinerary.getName() + "\n" + itinerary.getStartDate().toString() + "\n"
                    + itinerary.getEndDate().toString() + "\n" + itinerary.getHotelLocation().toString() + "\n");
            if (itinerary.getList().size() != itinerary.getNumberOfDays()) {
                throw new FileNotSavedException(file);
            }
            for (Agenda agenda : itinerary.getList()) {
                writer.write(agenda.toString());
            }
            writer.close();
        } catch (IOException e) {
            throw new FileNotSavedException(file);
        }
    }

    /**
     * Reads recommendations from filepath.
     *
     * @throws FileLoadFailException If the file fails to load.
     */
    public Itinerary readRecommendations() throws FileLoadFailException {
        List<Agenda> agendaList = new ArrayList<>();
        Itinerary itinerary;
        try {
            File file = new File(SAMPLE_RECOMMENDATIONS_FILE_PATH);
            Scanner scanner = new Scanner(file);
            String name = scanner.nextLine();
            LocalDateTime start = ParserTimeUtil.parseStringToDate(scanner.nextLine());
            LocalDateTime end = ParserTimeUtil.parseStringToDate(scanner.nextLine());
            Venue hotel = ParserStorageUtil.getVenueFromStorage(scanner.nextLine());
            itinerary = new Itinerary(start, end, hotel, name);
            while (scanner.hasNext()) {
                List<Venue> venueList = new ArrayList<>();
                List<Todo> todoList;
                final int number = ParserStorageUtil.getNumberFromStorage(scanner.nextLine());
                venueList.add(ParserStorageUtil.getVenueFromStorage(scanner.nextLine()));
                venueList.add(ParserStorageUtil.getVenueFromStorage(scanner.nextLine()));
                todoList = ParserStorageUtil.getTodoListFromStorage(scanner.nextLine());
                Agenda agenda = new Agenda(todoList, venueList, number);
                agendaList.add(agenda);
            }
            scanner.close();
            itinerary.setTasks(agendaList);
        } catch (FileNotFoundException | ParseException e) {
            throw new FileLoadFailException(SAMPLE_RECOMMENDATIONS_FILE_PATH);
        }
        return itinerary;
    }

    /**
     * Writes the specified itineraries name to the table of contents.
     *
     * @param itinerary This itineraries name is to be stored.
     * @throws FileNotSavedException If the file fails to save.
     */
    public void writeItinerarySave(Itinerary itinerary) throws FileNotSavedException {
        try {
            FileWriter writer = new FileWriter(ITINERARY_LIST_FILE_PATH, true);
            File file = new File(ITINERARY_LIST_FILE_PATH);
            int linecount = 0;
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            while (br.readLine() != null) {
                linecount++;
            }
            fr.close();
            writer.write(++linecount + " | " + itinerary.getName() + "\n");
            writer.close();
        } catch (IOException e) {
            throw new FileNotSavedException(ITINERARY_LIST_FILE_PATH);
        }
    }

    /**
     * Returns the itinerary list table of contents so user may refer to it and perform showItinerary command.
     *
     * @return Returns the String containing the table of contents
     * @throws FileLoadFailException   If the file fails to load.
     */
    public String readItineraryList() throws FileLoadFailException {
        StringBuilder output = new StringBuilder();
        try {
            File f = new File(ITINERARY_LIST_FILE_PATH);
            Scanner scanner = new Scanner(f);
            while (scanner.hasNext()) {
                String input = scanner.nextLine();
                String number = input.split("\\|", 2)[0].strip();
                String name = input.split("\\|", 2)[1].strip();
                output.append(number).append(". ").append(name).append("\n");
            }
        } catch (FileNotFoundException e) {
            throw new FileLoadFailException(ITINERARY_LIST_FILE_PATH);
        }
        return output.toString();
    }

    /**
     * Retrieves an itinerary from persistent storage based on its serial number.
     *
     * @param number The itineraries serial number.
     * @throws FileLoadFailException   If the file fails to load.
     */
    public Itinerary getItinerary(String number) throws FileLoadFailException {
        Itinerary itinerary = null;
        try {
            File itineraryTable = new File(ITINERARY_LIST_FILE_PATH);
            Scanner s = new Scanner(itineraryTable);
            String name = null;
            while (s.hasNext()) {
                String input = s.nextLine();
                String number1 = input.split("\\|", 2)[0].strip();
                if (number.equals(number1)) {
                    name = input.split("\\|", 2)[1].strip();
                }
            }
            File itinerariesFile = new File(ITINERARIES_FILE_PATH);
            Scanner scanner = new Scanner(itinerariesFile);
            AgendaList agendaList = new AgendaList();
            while (scanner.hasNext()) {
                if (scanner.nextLine().equals(name)) {
                    LocalDateTime start = ParserTimeUtil.parseStringToDate(scanner.nextLine());
                    LocalDateTime end = ParserTimeUtil.parseStringToDate(scanner.nextLine());
                    Venue hotel = ParserStorageUtil.getVenueFromStorage(scanner.nextLine());
                    itinerary = new Itinerary(start, end, hotel, name);
                    String fileLine = scanner.nextLine();
                    while (fileLine.split("\\|")[0].equals("Agenda ")) {
                        List<Venue> venueList = new ArrayList<>();
                        List<Todo> todoList;
                        final int number2 = Integer.parseInt(fileLine.split("\\|")[1]);
                        String newVenue = scanner.nextLine();
                        while (newVenue.contains(" |")) {
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
                }
            }
            s.close();
            scanner.close();
            assert itinerary != null;
            itinerary.setTasks(agendaList);
            return itinerary;
        } catch (FileNotFoundException | ParseException e) {
            throw new FileLoadFailException(ITINERARY_LIST_FILE_PATH);
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
}
