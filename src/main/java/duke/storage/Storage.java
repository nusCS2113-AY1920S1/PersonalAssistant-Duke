package duke.storage;

import duke.commons.exceptions.DukeException;
import duke.commons.Messages;
import duke.logic.parsers.ParserStorageUtil;
import duke.model.TaskList;
import duke.model.events.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Manages storage of Duke data in local storage.
 */
public class Storage {
    private TaskList tasks;
    private static final String BUS_FILE_PATH = "data/bus.txt";
    private static final String TRAIN_FILE_PATH = "data/train.txt";
    private static final String EVENTS_FILE_PATH = "data/events.txt";
    private static final String RECOMMENDATIONS_FILE_PATH = "data/recommendations.txt";
    private static final String ROUTES_FILE_PATH = "data/routes.txt";
    //private List<BusStop> allBusStops;
    //private List<TrainStation> allTrainStations;
    //private List<Route> userRoutes;

    /**
     * Constructs a Storage object that contains information fro the model.
     */
    public Storage() throws DukeException {
        tasks = new TaskList();
        read();
    }

    /**
     * Reads tasks from filepath. Creates empty tasks if file cannot be read.
     */
    private void read() throws DukeException {
        readEvents();
    }

    protected void readEvents() throws DukeException {
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
}
