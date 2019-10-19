package duke.storage;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import duke.logic.parsers.ParserStorageUtil;
import duke.model.TaskList;
import duke.model.events.Task;
import duke.logic.CreateMap;
import duke.model.locations.BusStop;
import duke.model.transports.BusService;
import duke.model.locations.Venue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private static final String TRAIN_FILE_PATH = "data/train.txt";
    private static final String EVENTS_FILE_PATH = "data/events.txt";
    private static final String ROUTES_FILE_PATH = "data/routes.txt";
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
        readEvent();
        readMap();
    }

    /**
     * Reads bus map from filepath.
     */
    private void readMap() throws DukeException {
        HashMap<String, BusStop> busStopData = new HashMap<>();
        HashMap<String, BusService> busData = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(getClass().getResourceAsStream(BUS_FILE_PATH)));
            String line;
            boolean isBusData = false;
            while ((line = br.readLine()) != null) {
                logger.log(Level.FINEST, line);
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
            br.close();
            this.map = new CreateMap(busStopData, busData);
        } catch (IOException e) {
            throw new DukeException(Messages.RESOURCE_NOT_FOUND + BUS_FILE_PATH);
        }
    }

    /**
     * Reads tasks from filepath. Creates empty tasks if file cannot be read.
     */
    private void readEvent() throws DukeException {
        List<Task> newTasks = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(EVENTS_FILE_PATH));
            String line;
            while ((line = br.readLine()) != null) {
                logger.log(Level.FINEST, line);
                newTasks.add(ParserStorageUtil.createTaskFromStorage(line));
            }
            br.close();
        } catch (FileNotFoundException e) {
            throw new DukeException(Messages.FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new DukeException(Messages.CORRUPTED_TASK);
        }
        tasks.setTasks(newTasks);
    }

    /**
     * Returns Venues fetched from stored memory.
     *
     * @return The List of all Venues in Recommendations list.
     */

    public List<Venue> readVenues() throws DukeException {
        List<Venue> recommendations = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(getClass().getResourceAsStream(RECOMMENDATIONS_FILE_PATH)));
            String line;
            while ((line = br.readLine()) != null) {
                logger.log(Level.FINEST, line);
                recommendations.add(ParserStorageUtil.getVenueFromStorage(line));
            }
            br.close();
        } catch (IOException e) {
            throw new DukeException(Messages.RESOURCE_NOT_FOUND + RECOMMENDATIONS_FILE_PATH);
        }
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

    public TaskList getTasks() {
        return tasks;
    }

    public CreateMap getMap() {
        return this.map;
    }
}
