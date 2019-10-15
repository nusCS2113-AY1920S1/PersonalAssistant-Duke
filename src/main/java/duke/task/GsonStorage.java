package duke.task;

import com.google.gson.Gson;
import duke.exception.DukeFatalException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;


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
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
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
    }

    /**
     * Loads all the patients in the Jsonfilen to the patient hashmap for quick patient lookup (Deserialization).
     *
     * @throws DukeFatalException If data file cannot be setup.
     */
    public void loadPatientHashMap() throws DukeFatalException {
        try {
            String json = Files.readString(Paths.get(filePath), StandardCharsets.US_ASCII);
            Patient[] patientList = new Gson().fromJson(json, Patient[].class);
            for (Patient patient : patientList) {
                addPatientToMap(patient);
            }
        } catch (IOException excp) {
            throw new DukeFatalException("Unable to load data file, try checking your permissions?");
        }
    }

    /**
     * Creates a list with the patients in the patient hash map and add the lists json representation
     * to the json file (Serialization).
     *
     * @throws DukeFatalException If the file writer cannot be setup.
     */
    public void writeJsonFile() throws DukeFatalException {
        ArrayList<Patient> patientArrList = new ArrayList<Patient>(patientMap.values());
        try {
            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.write(new Gson().toJson(patientArrList));
            fileWriter.close();
        } catch (IOException e) {
            throw new DukeFatalException("Unable to write data! Some data may have been lost,");
        }
    }

    /**
     * Adds a patient object to the hash map with all the patients - used when testing.
     */
    public void addPatientToMap(Patient patient) {
        patientMap.put(patient.getName(), patient);
    }

    public Patient getPatient(String name) {
        return patientMap.get(name);
    }

    public String getFilePath() {
        return filePath;
    }

    /**
     * Clears the json file and the paitent hash map - used when testing.
     */
    public void resetAllData() throws IOException {
        FileWriter fileWriter = new FileWriter(jsonFile);
        fileWriter.close();
        patientMap.clear();
    }
}