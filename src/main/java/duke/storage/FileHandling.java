package duke.storage;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import duke.exceptions.DukeException;
import duke.tasks.Deadline;
import duke.tasks.DoAfter;
import duke.tasks.DoWithin;
import duke.tasks.Event;
import duke.tasks.FixedDuration;
import duke.tasks.Recurring;
import duke.tasks.Task;
import duke.tasks.TaskList;
import duke.tasks.Todo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandling {
    private String file;

    public FileHandling(String file) {
        this.file = file;
    }

    /**
     * This function handles loading data from the file.
     *
     * @return a list that stores the tasks loaded from the file.
     * @throws DukeException when there are errors while handling the file.
     */
    public TaskList retrieveData() throws DukeException {

        try {
            FileInputStream readFile = new FileInputStream(this.file);
            TaskList tasks = getObjectMapper().readValue(readFile, TaskList.class);
            return tasks;

        } catch (FileNotFoundException e) {
            throw new DukeException(" Could not find the file. Invalid file name/file path... "
                    + "Will continue with an empty list");
        } catch (IOException e) {
            throw new DukeException(" Error while reading data from the file. "
                    + "Will continue with an empty list");
        }
    }

    /**
     * This function is responsible for saving data from the list into the file.
     *
     * @param storeDataInFile list of tasks that are to be stored in the file.
     * @throws DukeException when there are errors while loading data into the file.
     */
    public void saveData(TaskList storeDataInFile) throws DukeException {

        try {
            FileOutputStream write = new FileOutputStream(this.file);
            getObjectMapper().writeValue(write, storeDataInFile);
            write.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new DukeException(" Error occurred while writing data to the file");
        }
    }

    private ObjectMapper getObjectMapper() {
        return new ObjectMapper()
                .enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)
                .disable(MapperFeature.AUTO_DETECT_CREATORS,
                        MapperFeature.AUTO_DETECT_FIELDS,
                        MapperFeature.AUTO_DETECT_GETTERS,
                        MapperFeature.AUTO_DETECT_IS_GETTERS);
    }
}


