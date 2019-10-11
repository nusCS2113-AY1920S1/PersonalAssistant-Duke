package duke.task;

import com.google.gson.Gson;
import duke.exception.DukeFatalException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

// change name of this class to just Storage later when the Storage class have been removed

public class GsonStorage {

    private final File jsonFile;
    private final String filePath;
    private HashMap<String, Patient> patientMap = new HashMap<String, Patient>();

    /**
     * Looks if a Json file exists at the specified filepath and creates one if it does not exist.
     *
     * @throws DukeFatalException If data file cannot be setup.
     */

    public GsonStorage(String path) throws DukeFatalException {

        filePath = path;
        jsonFile = new File(filePath);
        if (!jsonFile.exists()) {
            try {
                if (!jsonFile.createNewFile()) {
                    throw new IOException();
                }
            } catch (IOException excp) {
                throw new DukeFatalException("Unable to setup data file, try checking your permissions?");
            }
        }
        // remove these later
        //loadPatientHashMap();
        loadHashMapWithDummyPatients();
        writeJsonFile();
    }

    /**
     * Loads all the patients in the Jsonfilen to the patient hashmap for quick patient lookup (Deserialization)
     *
     * @throws DukeFatalException If data file cannot be setup.
     */
    private void loadPatientHashMap() throws DukeFatalException {
        try {
            System.out.println("HALLÅ");
            String content = Files.readString(Paths.get(filePath), StandardCharsets.US_ASCII);
            Patient patient = new Gson().fromJson(content, Patient.class);                            // this can only handle one patient - need to fix so it can handle many
            //Patient[] patientList = new Gson().fromJson(content, Patient[].class);                  //this line causes error - null pointer
        } catch (IOException excp) {
            throw new DukeFatalException("Unable to load data file, try checking your permissions?");
        } catch (com.google.gson.JsonSyntaxException e) {
            throw new DukeFatalException("Data file has been corrupted!");
        } catch (Exception e) {
            throw new DukeFatalException("Data file has been corrupted!");
        } //remove general exception later 
    }

    /**
     * Converts all the patients in the patient hashmap to their Json representation and loads to the json file (Serialization)
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
        }
    }

    // remove later just to se if it works (or change to a testfile)
    private void loadHashMapWithDummyPatients() {
        Patient dummy1 = new Patient("dummy1", 100, "nuts");
        Patient dummy2 = new Patient("dummy2", 200, "no allergies");
        Patient dummy3 = new Patient("dummy3", 300, "cats");
        Patient dummy4 = new Patient("dummy4", 400, "nuts");
        patientMap.put(dummy1.getName(), dummy1);
        patientMap.put(dummy2.getName(), dummy2);
        patientMap.put(dummy3.getName(), dummy3);
        patientMap.put(dummy4.getName(), dummy4);
    }
}

