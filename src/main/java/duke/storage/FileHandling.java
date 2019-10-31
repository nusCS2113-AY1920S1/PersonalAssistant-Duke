package duke.storage;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import duke.exceptions.DukeException;
import duke.models.LockerList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


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
    public LockerList retrieveData() throws DukeException {

        try {
            FileInputStream readFile = new FileInputStream(this.file);
            LockerList lockers = getObjectMapper().readValue(readFile, LockerList.class);
            readFile.close();
            return lockers;

        } catch (FileNotFoundException e) {
            throw new DukeException(" Could not find the file. Invalid file name/file path... "
                    + "Will continue with an empty list");
        } catch (IOException e) {
            throw new DukeException(" Unable to read file. Will start with an empty list");
        }
    }

    /**
     * This function is responsible for saving data from the list into the file.
     *
     * @param storeDataInFile list of tasks that are to be stored in the file.
     * @throws DukeException when there are errors while loading data into the file.
     */
    public void saveData(LockerList storeDataInFile) throws DukeException {

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


