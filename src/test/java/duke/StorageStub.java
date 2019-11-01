package duke;

import duke.commons.exceptions.CorruptedFileException;
import duke.commons.exceptions.DukeDateTimeParseException;
import duke.commons.exceptions.DukeDuplicateTaskException;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.FileLoadFailException;
import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.ItineraryInsufficientAgendasException;
import duke.commons.exceptions.ParseException;
import duke.commons.exceptions.RecommendationDayExceededException;
import duke.commons.exceptions.RouteNodeDuplicateException;
import duke.commons.exceptions.StorageFileNotFoundException;
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

public class StorageStub {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private EventList events;
    private RouteList routes;
    private TransportationMap map;
    private ProfileCard profileCard;
    private static final String BUS_FILE_PATH = "/data/bus.txt";
    private static final String RECOMMENDATIONS_FILE_PATH = "/data/recommendations.txt";
    private static final String ITINERARIES_FILE_PATH = "testItineraries.txt";
    private static final String TRAIN_FILE_PATH = "/data/train.txt";
    private static final String EVENTS_FILE_PATH = "events.txt";
    private static final String ROUTES_FILE_PATH = "routes.txt";
    private static final String SAMPLE_RECOMMENDATIONS_FILE_PATH = "testSamples.txt";
    private static final String ITINERARY_LIST_FILE_PATH = "testItineraryTable.txt";

    /**
     * Constructs a Storage object that contains information from the model.
     */
    public StorageStub() {
        events = new EventList();
        routes = new RouteList();
        try {
            read();
        } catch (DukeException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    /**
     * Reads all storage file.
     */
    private void read() throws RouteNodeDuplicateException, CorruptedFileException, StorageFileNotFoundException,
            DukeDuplicateTaskException, DukeDateTimeParseException {
        readBus();
        readTrain();
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
     * @throws DukeDateTimeParseException   If the datetime of an event cannot be parsed.
     * @throws DukeDuplicateTaskException   If there is a duplicate event.
     * @throws StorageFileNotFoundException If the file cannot be read.
     */
    private void readEvent() throws DukeDuplicateTaskException, StorageFileNotFoundException {
        List<Event> events = new ArrayList<>();
        try {
            File f = new File(EVENTS_FILE_PATH);
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                events.add(ParserStorageUtil.createTaskFromStorage(s.nextLine()));
            }
            s.close();
        } catch (FileNotFoundException | ParseException e) {
            throw new StorageFileNotFoundException(EVENTS_FILE_PATH);
        }
        this.events.setEvents(events);
    }

    /**
     * Reads routes from filepath. Creates empty routes if file cannot be read.
     *
     * @throws RouteNodeDuplicateException  If there is a duplicate route that is read.
     * @throws CorruptedFileException       If the reading has failed.
     * @throws StorageFileNotFoundException If the storage file cannot be found.
     */
    private void readRoutes() throws RouteNodeDuplicateException, CorruptedFileException, StorageFileNotFoundException {
        List<Route> newRoutes = new ArrayList<>();
        try {
            File f = new File(ROUTES_FILE_PATH);
            Scanner s = new Scanner(f);
            Route newRoute = new Route(new ArrayList<>(), "", "");
            while (s.hasNext()) {
                String input = s.nextLine();
                if (input.split("\\|", 2)[0].strip().equals("route")) {
                    if (newRoute.getNumNodes() != 0) {
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
        } catch (FileNotFoundException e) {
            throw new StorageFileNotFoundException(ROUTES_FILE_PATH);
        }

        routes.setRoutes(newRoutes);
    }

    /**
     * Returns Venues fetched from stored memory.
     *
     * @return The List of all Venues in Recommendations list.
     */
    public List<Agenda> readVenues(int numDays) throws RecommendationDayExceededException {
        List<Agenda> recommendations = new ArrayList<>();
        Scanner s = new Scanner(getClass().getResourceAsStream(RECOMMENDATIONS_FILE_PATH));
        int i = 1;
        if (numDays > 8) {
            throw new RecommendationDayExceededException();
        }
        while (s.hasNext() && i <= numDays) {
            List<Venue> venueList = new ArrayList<>();
            venueList.add(ParserStorageUtil.getVenueFromStorage(s.nextLine()));
            List<Todo> todoList = ParserStorageUtil.getTodoListFromStorage(s.nextLine());
            venueList.add(ParserStorageUtil.getVenueFromStorage(s.nextLine()));
            todoList.addAll(ParserStorageUtil.getTodoListFromStorage(s.nextLine()));
            Agenda agenda = new Agenda(todoList, venueList, i++);
            recommendations.add(agenda);
        }
        s.close();

        return recommendations;
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
    public void writeItineraries(Itinerary itinerary, int type) throws ItineraryInsufficientAgendasException,
            FileNotSavedException {
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
                throw new ItineraryInsufficientAgendasException();
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
     * @throws DukeDateTimeParseException If the datetime cannot be parsed.
     * @throws FileLoadFailException      If the file fails to load.
     */
    public Itinerary readRecommendations() throws DukeDateTimeParseException, FileLoadFailException {
        List<Agenda> agendaList = new ArrayList<>();
        Itinerary itinerary;
        try {
            File f = new File(SAMPLE_RECOMMENDATIONS_FILE_PATH);
            Scanner s = new Scanner(f);
            String name = s.nextLine();
            LocalDateTime start = ParserTimeUtil.parseStringToDate(s.nextLine());
            LocalDateTime end = ParserTimeUtil.parseStringToDate(s.nextLine());
            Venue hotel = ParserStorageUtil.getVenueFromStorage(s.nextLine());
            itinerary = new Itinerary(start, end, hotel, name);
            while (s.hasNext()) {
                List<Venue> venueList = new ArrayList<>();
                List<Todo> todoList;
                final int number = ParserStorageUtil.getNumberFromStorage(s.nextLine());
                venueList.add(ParserStorageUtil.getVenueFromStorage(s.nextLine()));
                venueList.add(ParserStorageUtil.getVenueFromStorage(s.nextLine()));
                todoList = ParserStorageUtil.getTodoListFromStorage(s.nextLine());
                Agenda agenda = new Agenda(todoList, venueList, number);
                agendaList.add(agenda);
            }
            s.close();
            itinerary.setTasks(agendaList);
        } catch (FileNotFoundException | ParseException e) {
            throw new FileLoadFailException(new File(SAMPLE_RECOMMENDATIONS_FILE_PATH));
        }
        return itinerary;
    }

    /**
     * Writes the specified itineraries name to the table of contents.
     *
     * @param itinerary This itineraries name is to be stored.
     * @throws FileNotSavedException      If the file fails to save.
     */
    public void writeItinerarySave(Itinerary itinerary) throws FileNotSavedException {
        try {
            File f1 = new File(ITINERARY_LIST_FILE_PATH);
            int linecount = 0;
            FileReader fr = new FileReader(f1);
            BufferedReader br = new BufferedReader(fr);
            while (br.readLine() != null) {
                linecount++;
            }
            fr.close();
            br.close();
            FileWriter writer = new FileWriter(ITINERARY_LIST_FILE_PATH, true);
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
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String input = s.nextLine();
                String number = input.split("\\|", 2)[0].strip();
                String name = input.split("\\|", 2)[1].strip();
                output.append(number).append(". ").append(name).append("\n");
            }
            s.close();
        } catch (FileNotFoundException e) {
            throw new FileLoadFailException(new File(ITINERARY_LIST_FILE_PATH));
        }
        return output.toString();
    }

    /**
     * Retrieves an itinerary from persistent storage based on its name.
     *
     * @param name The itineraries name.
     * @throws FileLoadFailException   If the file fails to load.
     */
    public Itinerary getItinerary(String name) throws DukeException {
        Itinerary itinerary = null;
        try {
            File z = new File(ITINERARIES_FILE_PATH);
            Scanner s1 = new Scanner(z);
            AgendaList agendaList = new AgendaList();
            while (s1.hasNext()) {
                if (s1.nextLine().equals(name)) {
                    LocalDateTime start = ParserTimeUtil.parseStringToDate(s1.nextLine());
                    LocalDateTime end = ParserTimeUtil.parseStringToDate(s1.nextLine());
                    Venue hotel = ParserStorageUtil.getVenueFromStorage(s1.nextLine());
                    itinerary = new Itinerary(start, end, hotel, name);
                    String s2 = s1.nextLine();
                    while (s2.split("\\|")[0].equals("Agenda ")) {
                        List<Venue> venueList = new ArrayList<>();
                        List<Todo> todoList;
                        final int number2 = Integer.parseInt(s2.split("\\|")[1]);
                        String newVenue = s1.nextLine();
                        while (newVenue.contains(" |")) {
                            venueList.add(ParserStorageUtil.getVenueFromStorage(newVenue));
                            newVenue = s1.nextLine();
                        }
                        todoList = ParserStorageUtil.getTodoListFromStorage(newVenue);
                        Agenda agenda = new Agenda(todoList, venueList, number2);
                        agendaList.add(agenda);
                        if (s1.hasNextLine()) {
                            s2 = s1.nextLine();
                        } else {
                            break;
                        }
                    }
                }
            }
            s1.close();
            assert itinerary != null;
            itinerary.setTasks(agendaList);
            return itinerary;
        } catch (FileNotFoundException e) {
            throw new FileLoadFailException(new File(ITINERARY_LIST_FILE_PATH));
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

    public boolean getIsNewUser() {
        return profileCard.isNewUser();
    }
}
