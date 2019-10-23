import duke.data.GsonStorage;
import duke.data.Impression;
import duke.data.Patient;
import duke.data.PatientMap;
import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import org.junit.jupiter.api.Test;

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

    /**
     * A PatientMap object.
     */
    public PatientMap patientMap;

    /**
     * A GsonStorage object.
     */
    public GsonStorage storage;

    /**
     * a dummy patient used for testing.
     */
    private Patient dummy1 = new Patient("dummy1", "A100", "nuts");

    /**
     * a dummy patient used for testing.
     */
    private Patient dummy2 = new Patient("dummy2", "A200", null);

    /**
     * a dummy patient used for testing.
     */
    private Patient dummy3 = new Patient("dummy3", "A300", "cats");

    /**
     * a String containing the correct JSON representation of dummy1, dummy2 and dummy 3.
     */
    private String expected = "["
            + "{\"bedNo\":\"A300\",\"allergies\":\"cats\",\"impressions\":{},"
            //+ "\"height\":0,\"weight\":0,\"age\":0,\"number\":0,"
            + "\"name\":\"dummy3\"},"
            + "{\"bedNo\":\"A100\",\"allergies\":\"nuts\",\"impressions\":{},"
            //+ "\"height\":0,\"weight\":0,\"age\":0,\"number\":0,"
            + "\"name\":\"dummy1\"},"
            + "{\"bedNo\":\"A200\",\"impressions\":{},"
            //+ "\"height\":0,\"weight\":0,\"age\":0,\"number\":0,"
            + "\"name\":\"dummy2\"}]";

    /**
     * The constructor ig GsonStorateTest. Used to initialise the storage attribute.
     * Also resets all storage data.
     */
    GsonStorageTest() throws DukeFatalException, IOException {
        storage = new GsonStorage("data/patients.json");
        patientMap = storage.resetAllData();
    }

    /**
     * Creates a patient object and assign values to all of its attributes - used to test if the nesting works.
     *
     * @return the created patient object.
     */
    private Patient createComplexPatient() throws DukeException {
        Patient complexPatient = new Patient("Complexia", "C100", "cookies");
        Impression imp = complexPatient.addNewImpression(new Impression("Afraid", "bit me", complexPatient));
        complexPatient.setPriDiagnosis(imp.getName());
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
     *
     * @return A boolean stating if the storage function is working properly or not.
     */
    private boolean identical(Patient patient1, Patient patient2) {
        if (!(patient1.getBedNo().equals(patient2.getBedNo()))) {
            return false;
        } else if (!(java.util.Objects.equals(patient1.getAllergies(), patient2.getAllergies()))) {
            return false;
        } else if (!(java.util.Objects.equals(patient1.getHeight(), patient2.getHeight()))) {
            return false;
        } else if (!(java.util.Objects.equals(patient1.getWeight(), patient2.getWeight()))) {
            return false;
        } else if (!(java.util.Objects.equals(patient1.getAge(), patient2.getAge()))) {
            return false;
        } else if (!(java.util.Objects.equals(patient1.getNumber(), patient2.getNumber()))) {
            return false;
        } else if (!(java.util.Objects.equals(patient1.getAddress(), patient2.getAddress()))) {
            return false;
        } else {
            return java.util.Objects.equals(patient1.getHistory(), patient2.getHistory());
        }
    }

    /**
     * Tests if patients are transformed from the json file to the hash map properly.
     */
    @Test
    public void loadPatientHashMapTest() throws DukeException, IOException {
        patientMap = storage.resetAllData();
        FileWriter fileWriter = new FileWriter(storage.getFilePath());
        fileWriter.write(expected);
        fileWriter.close();
        patientMap = new PatientMap(storage);
        assertTrue(identical(patientMap.getPatient("A100"), dummy1));
        assertTrue(identical(patientMap.getPatient("A200"), dummy2));
        assertTrue(identical(patientMap.getPatient("A300"), dummy3));
    }

    /**
     * Creates the Json representation of a dummy patient.
     * Then recreates the patient objects based on what is in the json file.
     * When the recreation is done it checks if the first patients are identical to the new ones.
     */
    @Test
    public void identicalDummyPatient() throws IOException, DukeException, DukeFatalException {
        patientMap = storage.resetAllData();
        patientMap.addPatient(dummy1);
        storage.writeJsonFile(patientMap.getPatientHashMap());
        patientMap = new PatientMap(storage);
        Patient dummyPatientRecreated = patientMap.getPatient(dummy1.getBedNo());
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
        patientMap = storage.resetAllData();
        Patient complexPatient = createComplexPatient();
        patientMap.addPatient(complexPatient);
        storage.writeJsonFile(patientMap.getPatientHashMap());
        storage.loadPatientHashMap();
        Patient complexPatientRecreated = patientMap.getPatient("C100");
        boolean equals = identical(complexPatient, complexPatientRecreated);
        assertTrue(equals);
    }

    /**
     * Tests if patients are transformed from the hash map to the json file properly.
     */
    @Test
    public void writeJsonFileTest() throws DukeFatalException, IOException, DukeFatalException {
        patientMap = storage.resetAllData();
        patientMap.addPatient(dummy1);
        patientMap.addPatient(dummy2);
        patientMap.addPatient(dummy3);
        storage.writeJsonFile(patientMap.getPatientHashMap());
        String json = Files.readString(Paths.get(storage.getFilePath()), StandardCharsets.US_ASCII);
        System.out.println(expected);
        System.out.println("\n");
        System.out.println(json);
        assertEquals(expected, json);
    }
}