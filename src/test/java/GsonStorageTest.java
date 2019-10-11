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
 */
class GsonStorageTest {
    private String filePath = "data/patients.json";
    private GsonStorage gsonStorage = new GsonStorage(filePath);
    private Patient dummy1 = new Patient("dummy1", 100, "nuts");
    private Patient dummy2 = new Patient("dummy2", 200, null);
    private Patient dummy3 = new Patient("dummy3", 300, "cats");
    private String expected = "[{\"bedNo\":100,\"allergies\":\"nuts\",\"impressions\":[],\"height\":0,\"we"
            + "ight\":0,\"age\":0,\"number\":0,\"name\":\"dummy1\"},{\"bedNo\":200,\"impressions\":[],\"heig"
            + "ht\":0,\"weight\":0,\"age\":0,\"number\":0,\"name\":\"dummy2\"},{\"bedNo\":300,\"allergies\":\"ca"
            + "ts\",\"impressions\":[],\"height\":0,\"weight\":0,\"age\":0,\"number\":0,\"name\":\"dummy3\"}]";

    GsonStorageTest() throws DukeFatalException {
    }

    /**
     * Creates a patient object and assign values to all of its attributes - used to test if the nesting works.
     */
    private Patient createComplexPatient() throws DukeException {
        Patient complexPatient = new Patient("Complexia", 100, "cookies");
        complexPatient.addNewImpression(new Impression("Afraid", "bit me", complexPatient.getName()));
        complexPatient.setPriDiagnosis(0);
        complexPatient.setAllergies("dogs");
        complexPatient.setHeight(124);
        complexPatient.setWeight(250);
        complexPatient.setAge(84);
        complexPatient.setNumber(6582447);
        complexPatient.setAddress("Broadway 12a");
        complexPatient.setHistory("Operated the left arm in 2014 and have been feeling weak ever since");
        return complexPatient;
    }

    /**
     * Compares all the attributes of two patients and returns true if they all are the same, otherwise it returns
     * false.
     * TODO: compare impressions as well
     */
    boolean identical(Patient p1, Patient p2) {
        if (p1.getBedNo() != p2.getBedNo()) {
            return false;
        }
        if (!(p1.getAllergies() == null ? p2.getAllergies() == null : p1.getAllergies().equals(p2.getAllergies()))) {
            return false;
        }
        if (p1.getHeight() != p2.getHeight()) {
            return false;
        }
        if (p1.getWeight() != p2.getWeight()) {
            return false;
        }
        if (p1.getAge() != p2.getAge()) {
            return false;
        }
        if (p1.getNumber() != p2.getNumber()) {
            return false;
        }
        if (!(p1.getAddress() == null ? p2.getAddress() == null : p1.getAddress().equals(p2.getAddress()))) {
            return false;
        }
        if (!(p1.getHistory() == null ? p2.getHistory() == null : p1.getHistory().equals(p2.getHistory()))) {
            return false;
        }
        return true;
    }


    /**
     * Tests if patients are transformed from the json file to the hashmap properly.
     */
    @Test
    void loadPatientHashMapTest() throws DukeFatalException, IOException {
        gsonStorage.resetAllData();
        FileWriter fileWriter = new FileWriter(new File(gsonStorage.getFilePath()));
        fileWriter.write(expected);
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
        String json = Files.readString(Paths.get(filePath), StandardCharsets.US_ASCII);
        assertEquals(expected, json);
    }
}