import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.data.GsonStorage;
import duke.data.Impression;
import duke.data.Patient;
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
public class GsonStorageTest {

    private String filePath = "data/patients.json";
    private GsonStorage gsonStorage = new GsonStorage(filePath);
    private Patient dummy1 = new Patient("dummy1", "A100", "nuts");
    private Patient dummy2 = new Patient("dummy2", "A200", null);
    private Patient dummy3 = new Patient("dummy3", "A300", "cats");
    /*private String expected = "[{\"bedNo\":\"A100\",\"allergies\":\"nuts\",\"impressions\":[],\"height\":0,\"we"
            + "ight\":0,\"age\":0,\"number\":0,\"name\":\"dummy1\"},{\"bedNo\":\"A200\",\"impressions\":[],\"heig"
            + "ht\":0,\"weight\":0,\"age\":0,\"number\":0,\"name\":\"dummy2\"},{\"bedNo\":\"A300\",\"allergies\":\"ca"
            + "ts\",\"impressions\":[],\"height\":0,\"weight\":0,\"age\":0,\"number\":0,\"name\":\"dummy3\"}]";*/
    private String expected = "["
            + "{\"bedNo\":\"A300\",\"allergies\":\"cats\",\"impressions\":[],\"height\":0,"
            + "\"weight\":0,\"age\":0,\"number\":0,\"name\":\"dummy3\"},"
            + "{\"bedNo\":\"A100\",\"allergies\":\"nuts\",\"impressions\":[],\"height\":0,"
            + "\"weight\":0,\"age\":0,\"number\":0,\"name\":\"dummy1\"},"
            + "{\"bedNo\":\"A200\",\"impressions\":[],\"height\":0,"
            + "\"weight\":0,\"age\":0,\"number\":0,\"name\":\"dummy2\"}]";

    GsonStorageTest() throws DukeFatalException, IOException {
        gsonStorage.resetAllData();
    }

    /**
     * Creates a patient object and assign values to all of its attributes - used to test if the nesting works.
     */
    private Patient createComplexPatient() throws DukeException {
        Patient complexPatient = new Patient("Complexia", "C100", "cookies");
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
    private boolean identical(Patient patient1, Patient patient2) {
        if (!(patient1.getBedNo().equals(patient2.getBedNo()))) {
            return false;
        } else if (!(java.util.Objects.equals(patient1.getAllergies(), patient2.getAllergies()))) {
            return false;
        } else if (patient1.getHeight() != patient2.getHeight()) {
            return false;
        } else if (patient1.getWeight() != patient2.getWeight()) {
            return false;
        } else if (patient1.getAge() != patient2.getAge()) {
            return false;
        } else if (patient1.getNumber() != patient2.getNumber()) {
            return false;
        } else if (!(java.util.Objects.equals(patient1.getAddress(), patient2.getAddress()))) {
            return false;
        } else {
            return java.util.Objects.equals(patient1.getHistory(), patient2.getHistory());
        }
    }


    /**
     * Tests if patients are transformed from the json file to the hashmap properly.
     */
    @Test
    public void loadPatientHashMapTest() throws DukeFatalException, IOException {
        gsonStorage.resetAllData();
        FileWriter fileWriter = new FileWriter(new File(gsonStorage.getFilePath()));
        fileWriter.write(expected);
        fileWriter.close();
        gsonStorage.loadPatientHashMap();
        assertTrue(identical(gsonStorage.getPatient("A100"), dummy1));
        assertTrue(identical(gsonStorage.getPatient("A200"), dummy2));
        assertTrue(identical(gsonStorage.getPatient("A300"), dummy3));
    }

    /**
     * Creates the Json representation of a dummy patient
     * Then recreates the patient objects based on what is in the json file.
     * When the recreation is done it checks if the first patients are identical to the new ones.
     */
    @Test
    public void identicalDummyPatient() throws IOException, DukeException, DukeFatalException {
        gsonStorage.resetAllData();
        gsonStorage.addPatientToMap(dummy1);
        gsonStorage.writeJsonFile();
        gsonStorage.loadPatientHashMap();
        Patient dummyPatientRecreated = gsonStorage.getPatient(dummy1.getBedNo());
        boolean equals = identical(dummy1, dummyPatientRecreated);
        assertTrue(equals);
    }

    /**
     * Creates the Json representation of a complex patient
     * Then recreates the patient objects based on what is in the json file.
     * When the recreation is done it checks if the first patients are identical to the new ones.
     */
    @Test
    public void identicalComplexPatient() throws IOException, DukeException, DukeFatalException {
        gsonStorage.resetAllData();
        Patient complexPatient = createComplexPatient();
        gsonStorage.addPatientToMap(complexPatient);
        gsonStorage.writeJsonFile();
        gsonStorage.loadPatientHashMap();
        Patient complexPatientRecreated = gsonStorage.getPatient("C100");
        boolean equals = identical(complexPatient, complexPatientRecreated);
        assertTrue(equals);
    }

    /**
     * Tests if patients are transformed from the hashmap to the json file properly.
     */
    @Test
    public void writeJsonFileTest() throws DukeFatalException, IOException, DukeFatalException {
        gsonStorage.resetAllData();
        gsonStorage.addPatientToMap(dummy1);
        gsonStorage.addPatientToMap(dummy2);
        gsonStorage.addPatientToMap(dummy3);
        gsonStorage.writeJsonFile();
        String json = Files.readString(Paths.get(filePath), StandardCharsets.US_ASCII);
        System.out.println(expected);
        System.out.println("\n");
        System.out.println(json);
        assertEquals(expected, json);
    }
}