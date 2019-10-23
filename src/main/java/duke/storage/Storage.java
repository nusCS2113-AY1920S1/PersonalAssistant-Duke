package duke.storage;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import duke.logic.parsers.ParserStorageUtil;
import duke.logic.parsers.ParserTimeUtil;
import duke.model.lists.TaskList;
import duke.model.Task;
import duke.logic.CreateMap;
import duke.model.locations.BusStop;
import duke.model.locations.TrainStation;
import duke.model.planning.Agenda;
import duke.model.planning.Itinerary;
import duke.model.planning.Todo;
import duke.model.transports.BusService;
import duke.model.locations.Venue;

import java.io.File;
import java.io.FileNotFoundException;
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
    private TaskList tasks;
    private CreateMap map;
    private static final String BUS_FILE_PATH = "/data/bus.txt";
    private static final String RECOMMENDATIONS_FILE_PATH = "/data/recommendations.txt";
    private static final String SAMPLE_RECOMMENDATIONS_FILE_PATH = "samples.txt";
    private static final String TRAIN_FILE_PATH = "/data/train.txt";
    private static final String EVENTS_FILE_PATH = "events.txt";
    //private static final String ROUTES_FILE_PATH = "/data/routes.txt";
    //private List<BusStop> allBusStops;
    //private List<TrainStation> allTrainStations;
    //private List<Route> userRoutes;

    /**
     * Constructs a Storage object that contains information fro the model.
     */
    public Storage() {
        tasks = new TaskList();
        try {
            read();
        } catch (DukeException e) {
            logger.log(Level.WARNING, "File path does not exists.");
        }
    }

    /**
     * Reads all storage file.
     */
    private void read() throws DukeException {
        readBus();
        readTrain();
        readEvent();
    }


    /**
     * Reads train from filepath.
     */
    protected void readTrain() {
        assert this.map != null : "Map must be created first";
        HashMap<String, TrainStation> trainMap = new HashMap<>();
        Scanner s = new Scanner(getClass().getResourceAsStream(TRAIN_FILE_PATH));
        while (s.hasNext()) {
            TrainStation newTrain = ParserStorageUtil.createTrainFromStorage(s.nextLine());
            trainMap.put(newTrain.getAddress(), newTrain);
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
        this.map = new CreateMap(busStopData, busData);

    }

    /**
     * Reads tasks from filepath. Creates empty tasks if file cannot be read.
     */
    private void readEvent() throws DukeException {
        List<Task> newTasks = new ArrayList<>();
        try {
            File f = new File(EVENTS_FILE_PATH);
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                newTasks.add(ParserStorageUtil.createTaskFromStorage(s.nextLine()));
            }
            s.close();
        } catch (FileNotFoundException e) {
            throw new DukeException(Messages.FILE_NOT_FOUND);
        }
        tasks.setTasks(newTasks);
    }

    /**
     * Returns Venues fetched from stored memory.
     *
     * @return The List of all Venues in Recommendations list.
     */

    public List<Agenda> readVenues(int numDays) {
        List<Agenda> recommendations = new ArrayList<>();
        Scanner s = new Scanner(getClass().getResourceAsStream(RECOMMENDATIONS_FILE_PATH));
        int i = 1;
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
     */
    public void write() throws DukeException {
        writeEvents();
    }

    private void writeEvents() throws DukeException {
        try {
            FileWriter writer = new FileWriter(EVENTS_FILE_PATH);
            for (Task task : tasks) {
                writer.write(ParserStorageUtil.toStorageString(task) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new DukeException(Messages.FILE_NOT_SAVE);
        }
    }

    /**
     * Writes recommendations to filepath.
     */
    public void writeRecommendations(Itinerary itinerary) throws DukeException {
        try {
            FileWriter writer = new FileWriter(SAMPLE_RECOMMENDATIONS_FILE_PATH);
            writer.write(itinerary.getStartDate().toString() + "\n" + itinerary.getEndDate().toString() + "\n"
                    + itinerary.getHotelLocation().toString() + "\n");
            for (Agenda agenda : itinerary.getList()) {
                writer.write(agenda.toString() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new DukeException(Messages.FILE_NOT_SAVE);
        }
    }

    /**
     * Reads recommendations from filepath.
     */
    public static Itinerary readRecommendations() throws DukeException {
        List<Agenda> agendaList = new ArrayList<>();
        Itinerary itinerary;
        try {
            File f = new File(SAMPLE_RECOMMENDATIONS_FILE_PATH);
            Scanner s = new Scanner(f);
            LocalDateTime start = ParserTimeUtil.parseStringToDate(s.nextLine());
            LocalDateTime end = ParserTimeUtil.parseStringToDate(s.nextLine());
            Venue hotel = ParserStorageUtil.getVenueFromStorage(s.nextLine());
            itinerary = new Itinerary(start,end,hotel);
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
        } catch (FileNotFoundException e) {
            throw new DukeException(Messages.FILE_NOT_FOUND);
        }
        return itinerary;
    }

    public TaskList getTasks() {
        return tasks;
    }

    public CreateMap getMap() {
        return this.map;
    }
}
