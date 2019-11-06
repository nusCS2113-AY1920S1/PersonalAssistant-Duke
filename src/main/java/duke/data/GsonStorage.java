package duke.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.exception.DukeResetException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

    private Gson gson;
    //private RuntimeTypeAdapterFactory<DukeObject> typeAdapterFactory;

    /**
     * Constructor for GsonStorage.
     * Checks if a Json file exists at the specified filepath and creates one if it does not exist.
     *
     * @throws DukeFatalException If data file cannot be setup.
     */
    public GsonStorage(String path) throws DukeFatalException, DukeResetException {
        /*typeAdapterFactory = RuntimeTypeAdapterFactory
                .of(DukeObject.class, "type")
                .registerSubtype(Patient.class, "type1")
                .registerSubtype(Impression.class, "type2")
                .registerSubtype(DukeData.class, "type3")
                .registerSubtype(Evidence.class, "type4")
                .registerSubtype(Treatment.class, "type5")
                .registerSubtype(Investigation.class, "type6")
                .registerSubtype(Plan.class, "type7")
                .registerSubtype(Medicine.class, "type8")
                .registerSubtype(Observation.class, "type9")
                .registerSubtype(Result.class, "type10");*/

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Treatment.class, new TreatmentAdaptor())
                .registerTypeAdapter(Evidence.class, new EvidenceAdaptor());
        gson = gsonBuilder.create();//new GsonBuilder().registerTypeAdapterFactory(typeAdapterFactory).create();

        File dataDir = new File("data");
        File reportDir = new File("data/reports");

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
    public ArrayList<Patient> loadPatients() throws DukeFatalException {
        ArrayList<Patient> patients = new ArrayList<>();
        try {
            String json = Files.readString(Paths.get(filePath), StandardCharsets.US_ASCII);
            Patient[] patientList = gson.fromJson(json, Patient[].class);
            if (patientList == null) {
                return patients;
            }
            for (Patient patient : patientList) {
                patients.add(patient);
            }
        } catch (IOException e) {
            throw new DukeFatalException("Unable to load data file, try checking your permissions?");
        }
        return patients;
    }

    /**
     * Creates a list with the patients in the hash map and writes the lists json representation
     * to the json file.
     *
     * @param patientList the hash map containng all the patients
     * @throws DukeFatalException If the file writer cannot be setup.
     */
    public void writeJsonFile(ArrayList<Patient> patientList) throws DukeFatalException {
        try {
            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.write(gson.toJson(patientList));
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
     * @return an empty PatientList object
     */
    public PatientList resetAllData() throws IOException {
        FileWriter fileWriter = new FileWriter(jsonFile);
        fileWriter.close();
        return new PatientList();
    }

    /**
     * Loads all the details in the JSON file to a hash map.
     *
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
        HashMap<String, HashMap<String, String>> helpMap;
        try {
            JsonReader reader = new JsonReader(new FileReader(helpFile));
            helpMap = gson.fromJson(reader, new TypeToken<HashMap<String, HashMap<String, String>>>() {
            }.getType());
        } catch (IOException e) {
            throw new DukeFatalException("Unable to load data file, try checking your permissions?");
        }
        return helpMap;
    }

    /* @@author gowgos5 */
    /**
     * Loads help details from a pre-defined JSON file.
     *
     * @param file Relative file path.
     * @return A list of {@code Help} objects.
     * @throws DukeException If the data file cannot be loaded.
     */
    public List<Help> loadHelpList(String file) throws DukeException {
        List<Help> helpList;

        try {
            JsonReader reader = new JsonReader(new FileReader(file));
            Help[] helps = gson.fromJson(reader, Help[].class);
            helpList = Arrays.asList(helps);
        } catch (IOException e) {
            throw new DukeException("The help data file cannot be loaded. The help window will be disabled.");
        }

        return helpList;
    }
}
