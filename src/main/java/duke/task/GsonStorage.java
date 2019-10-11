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
        loadPatientHashMap();
        //loadHashMapWithDummyPatients();
        //writeJsonFile();
    }

    /**
     * Loads all the patients in the Jsonfilen to the patient hashmap for quick patient lookup (Deserialization).
     *
     * @throws DukeFatalException If data file cannot be setup.
     */
    private void loadPatientHashMap() throws DukeFatalException {
        try {
            String json = Files.readString(Paths.get(filePath), StandardCharsets.US_ASCII);
            System.out.println("The raw json looks like: " + json);
            Patient[] patientList = new Gson().fromJson(json, Patient[].class);
            for (Patient patient : patientList) {
                patientMap.put(patient.getName(), patient);
                System.out.println(patient);
                if (patient instanceof Patient) {
                    System.out.println("They are patient objects with the following attributes!");
                    System.out.println("name: " + patient.getName());
                    System.out.println("adress: " + patient.getAddress());
                    System.out.println("bed no: " + patient.getBedNo());
                    System.out.println("printing the object itself gives: :" + patient);

                } else {
                    throw new DukeFatalException("Data file has been corrupted!");
                }
            }
            System.out.println("\nPatientlist looks like: " + patientList);
            System.out.println("Patient map looks like: " + patientMap);

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
    private void writeJsonFile() throws DukeFatalException {
        ArrayList<Patient> patientArrList = new ArrayList<Patient>();
        patientArrList.addAll(patientMap.values());
        try {
            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.write(new Gson().toJson(patientArrList));
            fileWriter.close();
        } catch (IOException excp) {
            throw new DukeFatalException("Unable to write data! Some data may have been lost,");
        }
    }

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

// don't forget;
    // change the name to storage later when storage class have been removed
    // remove the test functions in the constructor later
    // remove all the prints
    // maybe put in exception that handles if the user alters with the json file
    // move the dummy function to a testfile
    // change back the toString method in patients
    // change the filePath in DukeLauncher to point at the json file instead of the tsv file
