package duke.storage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import duke.commons.DukeException;
import duke.task.Task;
import duke.task.TaskList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Stores tasks in file and reads tasks from file.
 */
public class Storage {

    private String path;

    /**
     * Constructor for Storage.
     *
     * @param path the path to store the file, including the file name.
     */
    public Storage(String path) {
        this.path = path;
    }

    /**
     * Serialize TaskList to file.
     * @param taskList the TaskList to serialize.
     * @throws DukeException if fails to serialize due to IO exception.
     */
    public void serialize(TaskList taskList) throws DukeException {
        List<Task> tasks = taskList.getTasks();
        try {
            ObjectMapper mapper = new ObjectMapper();
            //mapper.setDateFormat(new SimpleDateFormat(TimeParser.getDatePattern()));
            mapper.writerWithType(new TypeReference<List<Task>>() {
            }).writeValue(new File(path), tasks);
        } catch (IOException i) {
            throw new DukeException("IO Exception");
        }
    }

    /**
     * Deserialize TaskList from file. If the file is not found, returns an empty TaskList.
     * @return a TaskList object.
     * @throws DukeException if file is damaged.
     */
    public TaskList deserialize() throws DukeException {
        List<Task> tasks = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            //mapper.setDateFormat(new SimpleDateFormat(TimeParser.getDatePattern()));
            tasks = mapper.readValue(new File(path), new TypeReference<List<Task>>() {
            });
            return new TaskList(tasks);
        } catch (IOException i) {
            if (i instanceof FileNotFoundException) {
                return new TaskList();
            } else {
                i.printStackTrace();
                throw new DukeException("IO exception when loading data");
            }
        }
    }
}
