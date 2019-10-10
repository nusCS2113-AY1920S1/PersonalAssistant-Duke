package duke.task;

import com.google.gson.Gson;
import duke.exception.DukeFatalException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// change name of this class to just Storage later when the Storage
// class have been removed

public class GsonStorage {

    private final File jsonFile;
    private HashMap<String, Patient> patientMap = new HashMap<String, Patient>();

    /**
     * Constructs a new Storage object, with the patient file at the specified
     * path, and create HashMap for quick patient
     * lookup.
     *
     * @throws DukeFatalException If data file cannot be setup.
     */

    public GsonStorage(String filePath) throws DukeFatalException {
        jsonFile = new File(filePath);
        if (!jsonFile.exists()) {
            try {
                if (!jsonFile.createNewFile()) {
                    throw new IOException();
                }
            } catch (IOException excp) {
                throw new DukeFatalException("Unable to setup data file, try checking your permissions?");
            }
        } else {
            System.out.println("json file exist!"); // remove later
            // so if you get here there is a json file and then all json representations of the patient
            // objects that are in the json file must be transformed into patients objects that should
            // be added to the hashmap  https://www.youtube.com/watch?v=ou2yFJ-NWr8&t=284s

        }
        writeJsonFile(); // remove later, just to see how the writeJsonFile works
    }

    /**
     * Creates the Json representation of every patient in the patient
     * hash map and then stores these representations in the Json file.
     *
     * @throws DukeFatalException If the file writer cannot be setup.
     */
    public void writeJsonFile() throws DukeFatalException {
        try {
            FileWriter fileWriter = new FileWriter(jsonFile);
            for (Map.Entry entry : patientMap.entrySet()) {
                String json = new Gson().toJson(entry.getValue());
                fileWriter.write(json);
            }
            fileWriter.close();
        } catch (IOException excp) {
            throw new DukeFatalException("Unable to write data! Some data may have been lost,");
        } // check if creating the fileWriter deletes everything in the json file or not
    }
}

