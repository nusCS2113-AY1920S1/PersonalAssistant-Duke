import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.task.GsonStorage;
import duke.task.Impression;
import duke.task.Patient;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * JUnit class testing the class GsonStorage.
 *
 */
class GsonStorageTest {
    private String filePath = "data/patients.json";
    private GsonStorage gsonStorage = new GsonStorage(filePath);
    private Patient dummy1 = new Patient("dummy1", 100, "nuts");
    private Patient dummy2 = new Patient("dummy2", 200, null);
    private Patient dummy3 = new Patient("dummy3", 300, "cats");

    GsonStorageTest() throws DukeFatalException {
    }

    /**
     * Creates a patient object and assign values to all of its attributes - used to test if the nesting works.
     */
    private Patient createComplexPatient() throws DukeException {
        Patient ComplexPatient1 = new Patient("Complexia", 100, "cookies");
        ComplexPatient1.addNewImpression(new Impression("Afraid", "Patient bit me in the finger 7 times", ComplexPatient1.getName()));
        ComplexPatient1.setPriDiagnosis(0);
        ComplexPatient1.setAllergies("dogs");
        ComplexPatient1.setHeight(124);
        ComplexPatient1.setWeight(250);
        ComplexPatient1.setAge(84);
        ComplexPatient1.setNumber(6582447);
        ComplexPatient1.setAddress("Broadway 12a");
        ComplexPatient1.setHistory("Operated the left arm in 2014 and have been feeling weak ever since");
        return ComplexPatient1;
    }

    /**
     * Compares all the attributes of two patients and returns true if they all are the same, otherwise it returns false
     * TODO: compare impressions as well
     */
    boolean identical(Patient patient1, Patient patient2) {
        if (patient1.getBedNo() != patient2.getBedNo()) {
            return false;
        }
        if (!(patient1.getAllergies() == null ? patient2.getAllergies() == null : patient1.getAllergies().equals(patient2.getAllergies()))) {
            return false;
        }
        if (patient1.getHeight() != patient2.getHeight()) {
            return false;
        }
        if (patient1.getWeight() != patient2.getWeight()) {
            return false;
        }
        if (patient1.getAge() != patient2.getAge()) {
            return false;
        }
        if (patient1.getNumber() != patient2.getNumber()) {
            return false;
        }
        if (!(patient1.getAddress() == null ? patient2.getAddress() == null : patient1.getAddress().equals(patient2.getAddress()))) {
            return false;
        }
        if (!(patient1.getImpressions().toString() == null ? patient2.getImpressions().toString() == null : patient1.getImpressions().toString().equals(patient2.getImpressions().toString()))) {
            return false;
        }
        return patient1.getHistory() == null ? patient2.getHistory() == null : patient1.getHistory().equals(patient2.getHistory());
    }


    /**
     * Tests if patients are transformed from the json file to the hashmap properly.
     */
    @Test
    void loadPatientHashMapTest() throws DukeFatalException, IOException {
        gsonStorage.resetAllData();
        FileWriter fileWriter = new FileWriter(new File(gsonStorage.getFilePath()));
        String jsonRepresentations = "[{\"bedNo\":100,\"allergies\":\"nuts\",\"impressions\":[],\"height\":0,\"weight\":0,\"age\":0,\"number\":0,\"name\":\"dummy1\"},{\"bedNo\":200,\"impressions\":[],\"height\":0,\"weight\":0,\"age\":0,\"number\":0,\"name\":\"dummy2\"},{\"bedNo\":300,\"allergies\":\"cats\",\"impressions\":[],\"height\":0,\"weight\":0,\"age\":0,\"number\":0,\"name\":\"dummy3\"}]";
        fileWriter.write(jsonRepresentations);
        fileWriter.close();
        gsonStorage.loadPatientHashMap();
        assertTrue(identical(gsonStorage.getPatient("dummy1"), dummy1));
        assertTrue(identical(gsonStorage.getPatient("dummy2"), dummy2));
        assertTrue(identical(gsonStorage.getPatient("dummy3"), dummy3));
    }

    /**
     * Creates the Json representation of a dummy patient
     * Then recreates the patient objects based on what is in the json file.
     * When the recreation is done it checks if the first patients are identical to the new ones.
     */
    @Test
    void identicalDummyPatient() throws IOException, DukeException {
        gsonStorage.resetAllData();
        gsonStorage.addPatientToMap(dummy1);
        gsonStorage.writeJsonFile();
        gsonStorage.loadPatientHashMap();
        Patient dummyPatientRecreated = gsonStorage.getPatient(dummy1.getName());
        Boolean equals = identical(dummy1, dummyPatientRecreated);
        assertTrue(equals);
    }

    /**
     * Creates the Json representation of a complex patient
     * Then recreates the patient objects based on what is in the json file.
     * When the recreation is done it checks if the first patients are identical to the new ones.
     */
    @Test
    void identicalComplexPatient() throws IOException, DukeException {
        gsonStorage.resetAllData();
        Patient complexPatient = createComplexPatient();
        gsonStorage.addPatientToMap(complexPatient);
        gsonStorage.writeJsonFile();
        gsonStorage.loadPatientHashMap();
        Patient complexPatientRecreated = gsonStorage.getPatient("Complexia");
        boolean equals = identical(complexPatient, complexPatientRecreated);
        assertTrue(equals);
    }

    /**
     * Tests if patients are transformed from the hashmap to the json file properly.
     */
    @Test
    void writeJsonFileTest() throws DukeFatalException, IOException {
        gsonStorage.resetAllData();
        gsonStorage.addPatientToMap(dummy1);
        gsonStorage.addPatientToMap(dummy2);
        gsonStorage.addPatientToMap(dummy3);
        gsonStorage.writeJsonFile();
        String expected = "[{\"bedNo\":100,\"allergies\":\"nuts\",\"impressions\":[],\"height\":0,\"weight\":0,\"age\":0,\"number\":0,\"name\":\"dummy1\"},{\"bedNo\":200,\"impressions\":[],\"height\":0,\"weight\":0,\"age\":0,\"number\":0,\"name\":\"dummy2\"},{\"bedNo\":300,\"allergies\":\"cats\",\"impressions\":[],\"height\":0,\"weight\":0,\"age\":0,\"number\":0,\"name\":\"dummy3\"}]";
        String json = Files.readString(Paths.get(filePath), StandardCharsets.US_ASCII);
        assertEquals(expected, json);
    }
}