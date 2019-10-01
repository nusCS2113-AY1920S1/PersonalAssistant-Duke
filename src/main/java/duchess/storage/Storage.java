package duchess.storage;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import duchess.logic.commands.exceptions.DukeException;
import duchess.model.task.TaskList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Storage {
    private String fileName;

    public Storage(String fileName) {
        this.fileName = fileName;
    }

    // Unchecked type coercion is necessary here.
    // And possible cast exceptions are handled

    /**
     * Returns the tasklist loaded from file.
     */
    @SuppressWarnings("unchecked")
    public TaskList load() throws DukeException {
        try {
            FileInputStream fileStream = new FileInputStream(this.fileName);
            TaskList taskList = getObjectMapper().readValue(fileStream, TaskList.class);
            fileStream.close();
            return taskList;
        } catch (IOException | ClassCastException e) {
            System.err.println(e);
            throw new DukeException("Unable to read file, continuing with empty list.");
        }
    }

    /**
     * Saves the given tasklist to file.
     *
     * @param taskList the tasklist to be saved
     * @throws DukeException an error if unable to write to file
     */
    public void save(TaskList taskList) throws DukeException {
        try {
            FileOutputStream fileStream = new FileOutputStream(this.fileName);
            getObjectMapper().writeValue(fileStream, taskList);
            fileStream.close();
        } catch (IOException e) {
            throw new DukeException("An unexpected error occurred when writing to the file. " + e);
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
