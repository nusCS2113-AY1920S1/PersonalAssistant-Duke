package duke.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import duke.exception.DukeFatalException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * Handles storage of patients.
 */

public class GsonStorage {

    /*
     * the file that the patients will be stored in.
     */
    private final File jsonFile;

    /*
     * the filepath to the file that the patients will be stored in.
     */
    private final String filePath;

    /**
     * Constructor for GsonStorage.
     * Checks if a Json file exists at the specified filepath and creates one if it does not exist.
     *
     * @throws DukeFatalException If data file cannot be setup.
     */
    public GsonStorage(String path) throws DukeFatalException {
        File dataDir = new File("data");
        File reportDir = new File("data/Reports");

        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
        if (!reportDir.exists()) {
            reportDir.mkdir();
        }
        filePath = path;
        jsonFile = new File(filePath);
        if (!jsonFile.exists()) {
            try {
                if (!jsonFile.createNewFile()) {
                    throw new IOException();
                }
            } catch (IOException e) {
                throw new DukeFatalException("Unable to setup data file, try checking your permissions?");
            }
        }
    }

    /**
     * Loads all the patients in the JSON file to a hash map.
     *
     * @return the hash map containing the patients
     * @throws DukeFatalException If data file cannot be setup.
     */
    public HashMap<String, Patient> loadPatientHashMap() throws DukeFatalException {
        HashMap<String, Patient> patientMap = new HashMap<>();
        try {
            String json = Files.readString(Paths.get(filePath), StandardCharsets.US_ASCII);
            Patient[] patientList = new Gson().fromJson(json, Patient[].class);
            if (patientList == null) {
                return patientMap;
            }
            for (Patient patient : patientList) {
                patientMap.put(patient.getBedNo(), patient);
            }
        } catch (IOException e) {
            throw new DukeFatalException("Unable to load data file, try checking your permissions?");
        }
        return patientMap;
    }

    /**
     * Creates a list with the patients in the hash map and writes the lists json representation
     * to the json file.
     *
     * @param patientMap the hash map containng all the patients
     * @throws DukeFatalException If the file writer cannot be setup.
     */
    public void writeJsonFile(HashMap<String, Patient> patientMap) throws DukeFatalException {
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
     * Returns the filepath to the json file containing the Json representation of all the patients.
     *
     * @return the filepath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Clears the json file and the paitent hash map. Used to reset the storage data.
     *
     * @return an empty PatientMap object
     */
    public PatientMap resetAllData() throws IOException {
        FileWriter fileWriter = new FileWriter(jsonFile);
        fileWriter.close();
        return new PatientMap();
    }

    /**
     * Loads all the details in the JSON file to a hash map.
     * @param helpFile the path of the helpFile
     * @return the hash map containing the helpfile
     * @throws DukeFatalException If data file cannot be setup.
     */
    public HashMap<String, HashMap<String, String>> loadHelpHashMap(String helpFile) throws DukeFatalException {
        final File jsonFile = new File(helpFile);
        if (!jsonFile.exists()) {
            try {
                if (!jsonFile.createNewFile()) {
                    throw new IOException();
                }
            } catch (IOException e) {
                throw new DukeFatalException("Unable to setup data file, try checking your permissions?");
            }
        }
        HashMap<String, HashMap<String, String>> helpMap = new HashMap<String, HashMap<String, String>>();
        try {
            JsonReader reader = new JsonReader(new FileReader(helpFile));
            helpMap = new Gson().fromJson(reader, new TypeToken<HashMap<String, HashMap<String,String>>>(){}.getType());
        } catch (IOException e) {
            throw new DukeFatalException("Unable to load data file, try checking your permissions?");
        }
        return helpMap;
    }

}
