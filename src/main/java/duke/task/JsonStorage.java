package duke.task;

import com.google.gson.Gson;
import duke.exception.DukeFatalException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class JsonStorage {

    private final File patientFile;
    private Gson gson;
    private HashMap<String, Patient> patientMap = new HashMap<String, Patient>();

    /**
     * Constructs a new Storage object, with the patient file at the specified path, and create HashMap for quick patient
     * lookup.
     *
     * @param filePath Path at which to look for or create the data file.
     * @throws DukeFatalException If data file cannot be setup.
     */

    public JsonStorage(String filePath) throws DukeFatalException {
        patientFile = new File("/Users/JacobT/Desktop/PLUGG/CS2131/DrDuke/data/patient.json"); // change to filePath later
        if (!patientFile.exists()) {
            try {
                if (!patientFile.createNewFile()) {
                    throw new IOException();
                }
            } catch (IOException excp) {
                throw new DukeFatalException("Unable to setup data file, try checking your permissions?");
            }
        }
        else{

            // add every patient from JsonFile to json - use
            // iterate json and add every patient to patientMap one by one

        }

    }

    public void writePatientFile() throws DukeFatalException {
        try {
            FileWriter patientFileWr = new FileWriter(patientFile);

            // clear jsonFile
            // clear json 
            // iterate patientMap and add every patient to json using in every iteration:
                  //  gson.toJson(patientName, patient)
            // transfer content of json to JsonFile using;     jsonFile.write(json.toJSONString());

            patientFileWr.close();
        } catch (IOException excp) {
            throw new DukeFatalException("Unable to write data! Some data may have been lost,");
        }
    }
}

