/* @@author JacobToresson */

package duke.data.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import duke.data.Evidence;
import duke.data.Help;
import duke.data.Patient;
import duke.data.PatientData;
import duke.data.Treatment;
import duke.exception.DukeFatalException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Handles storage of patients.
 */
public class GsonStorage {
    private static final String PATIENT_FILE = "/data/patients.json";

    /**
     * the file that the patients will be stored in.
     */
    private final File jsonFile;

    /**
     * the filepath to the file that the patients will be stored in.
     */
    private final String filePath;

    private Gson gson;
    //private RuntimeTypeAdapterFactory<DukeObject> typeAdapterFactory;

    /**
     * Constructor for GsonStorage.
     * Checks if a Json file exists at the specified filepath and creates one if it does not exist.
     *
     * @throws DukeFatalException If data files cannot be setup.
     */
    public GsonStorage(String path) throws DukeFatalException {
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
        DukeFatalException folderExcp =
                new DukeFatalException("Unable to setup data folders, try checking your permissions?");

        if (!dataDir.exists() && !dataDir.mkdir()) {
            throw folderExcp;
        }
        if (!reportDir.exists() && !reportDir.mkdir()) {
            throw folderExcp;
        }
        filePath = path;
        jsonFile = new File(filePath);
        if (!jsonFile.exists()) {
            try {
                if (!jsonFile.createNewFile()) {
                    throw new IOException();
                }
            } catch (IOException e) {
                throw folderExcp;
            }
        }
    }

    /**
     * Loads all the patients in the JSON file to a hash map.
     *
     * @return the hash map containing the patients
     * @throws DukeFatalException If data files cannot be setup.
     */
    public ArrayList<Patient> loadPatients() throws DukeFatalException {
        ArrayList<Patient> patients = new ArrayList<>();
        try {
            String json = Files.readString(Paths.get(filePath), StandardCharsets.US_ASCII);
            Patient[] patientArr = gson.fromJson(json, Patient[].class);

            if (patientArr == null) {
                InputStream is = getClass().getResourceAsStream(PATIENT_FILE);

                if (is != null) {
                    JsonReader reader = new JsonReader(new InputStreamReader(is));
                    patientArr = gson.fromJson(reader, Patient[].class);
                }
            }

            if (patientArr != null) {
                patients.addAll(Arrays.asList(patientArr));
            }
        } catch (IOException e) {
            throw new DukeFatalException("Unable to load data files, try checking your permissions?");
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
            throw new DukeFatalException("Unable to write data! Some data may have been lost.");
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
     * @return an empty PatientData object
     */
    public PatientData resetAllData() throws IOException {
        FileWriter fileWriter = new FileWriter(jsonFile);
        fileWriter.close();
        return new PatientData();
    }

    /* @@author gowgos5 */
    /**
     * Loads help details from a pre-defined JSON file.
     *
     * @param file Relative help file path.
     * @return A list of {@code Help} objects.
     */
    public List<Help> loadHelpList(String file) {
        List<Help> helpList = new ArrayList<>();

        InputStream is = getClass().getResourceAsStream(file);

        if (is != null) {
            JsonReader reader = new JsonReader(new InputStreamReader(is));
            Help[] helps = gson.fromJson(reader, Help[].class);
            helpList = Arrays.asList(helps);
        }

        return helpList;
    }
}
