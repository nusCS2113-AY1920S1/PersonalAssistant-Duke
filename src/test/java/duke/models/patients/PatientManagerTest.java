//@@author kkeejjuunn

package duke.models.patients;

import org.junit.jupiter.api.Test;

import duke.exceptions.DukeException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PatientManagerTest {
    @Test
    public void doesExistTest() {
        ArrayList<Patient> testPatientList = new ArrayList<>();
        Patient patient1 = new Patient(1,"Lisa","S1234567T","12A","Nil");
        testPatientList.add(patient1);
        Patient patient2 = new Patient(2,"Helen","S2234567I","3B","Nil");
        testPatientList.add(patient2);
        PatientManager patientManager = new PatientManager(testPatientList);

        assertTrue(patientManager.doesExist(1));
        assertTrue(patientManager.doesExist(2));
        assertFalse(patientManager.doesExist(3));
        assertFalse(patientManager.doesExist(4));
    }

    @Test
    public void getPatientTest() throws DukeException {
        ArrayList<Patient> testPatientList = new ArrayList<>();
        Patient patient1 = new Patient(1,"Lisa","S1234567T","12A","Nil");
        testPatientList.add(patient1);
        Patient patient2 = new Patient(2,"Helen","S2234567I","3B","Nil");
        testPatientList.add(patient2);
        PatientManager patientManager = new PatientManager(testPatientList);

        assertEquals(patientManager.getPatient(1),patient1);
        assertEquals(patientManager.getPatient(2),patient2);

        try {
            patientManager.getPatient(3);
        } catch (Exception e) {
            String expectedMessage = "Oops! The patient with id 3 does not exist.";
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void getPatientByNameTest() {
        ArrayList<Patient> testPatientList = new ArrayList<>();
        Patient patient1 = new Patient(1,"Lisa Chew","S1234567T","12A","Nil");
        testPatientList.add(patient1);
        Patient patient2 = new Patient(2,"Helen Teo","S2234567I","3B","Nil");
        testPatientList.add(patient2);
        Patient patient3 = new Patient(3,"Helen Kok","S2434597A","5D","Nil");
        testPatientList.add(patient3);
        Patient patient4 = new Patient(4,"","S2534569I","6C","Nil");
        testPatientList.add(patient4);
        PatientManager patientManager = new PatientManager(testPatientList);

        ArrayList<Patient> expectedPatientList1 = new ArrayList<>();
        expectedPatientList1.add(patient1);
        assertEquals(patientManager.getPatientByName("Lisa"),expectedPatientList1);

        ArrayList<Patient> expectedPatientList2 = new ArrayList<>();
        expectedPatientList2.add(patient2);
        expectedPatientList2.add(patient3);
        assertEquals(patientManager.getPatientByName("Helen"),expectedPatientList2);

        assertEquals(patientManager.getPatientByName("Anni"),new ArrayList<>());
    }

    @Test
    public void nameIsValidTest() throws DukeException {
        PatientManager patientManager = new PatientManager(new ArrayList<Patient>());
        try {
            patientManager.nameIsValid(",,");
        } catch (Exception e) {
            String expectedMessage = "Oops! The patient's name cannot contain any special characters.";
            assertEquals(expectedMessage, e.getMessage());
        }
        try {
            patientManager.nameIsValid("123");
        } catch (Exception e) {
            String expectedMessage = "Oops! The patient's name must have at least 3 alphabets.";
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void nricIsValidTest() throws DukeException {
        ArrayList<Patient> testPatientList = new ArrayList<>();
        Patient patient1 = new Patient(1,"Lisa Chew","S1234567T","12A","Nil");
        PatientManager patientManager = new PatientManager(testPatientList);

        try {
            patientManager.nricIsValid("S1234567T");
        } catch (Exception e) {
            String expectedMessage = "Oops! The NRIC is existed.";
            assertEquals(expectedMessage, e.getMessage());
        }

        try {
            patientManager.nricIsValid("A1234567*");
        } catch (Exception e) {
            String expectedMessage = "Oops! The patient's NRIC cannot contain any special characters.";
            assertEquals(expectedMessage, e.getMessage());
        }

        try {
            patientManager.nricIsValid("A1234567T");
        } catch (Exception e) {
            String expectedMessage = "Oops! The first letter of NRIC can only be S, T, F or G.";
            assertEquals(expectedMessage, e.getMessage());
        }

        try {
            patientManager.nricIsValid("S12345677I");
        } catch (Exception e) {
            String expectedMessage = "Oops! NRIC must contain exactly 9 characters.";
            assertEquals(expectedMessage, e.getMessage());
        }

        try {
            patientManager.nricIsValid("S123A568I");
        } catch (Exception e) {
            String expectedMessage = "Oops! The NRIC can only be numerical except the first and last character.";
            assertEquals(expectedMessage, e.getMessage());
        }

        try {
            patientManager.nricIsValid("S123A5686");
        } catch (Exception e) {
            String expectedMessage = "Oops! The last character of NRIC can only be alphabet.";
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void roomIsValidTest() throws DukeException {
        PatientManager patientManager = new PatientManager(new ArrayList<Patient>());
        try {
            patientManager.roomIsValid("");
        } catch (Exception e) {
            String expectedMessage = "Oops! The patient's Room No. cannot be empty.";
            assertEquals(expectedMessage, e.getMessage());
        }
        try {
            patientManager.roomIsValid("12A&");
        } catch (Exception e) {
            String expectedMessage = "Oops! The patient's Room No. cannot contain any special characters.";
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void addPatientTest() throws DukeException {
        ArrayList<Patient> testPatientList = new ArrayList<>();
        Patient patient1 = new Patient("Lisa Chew","S1234567T","12A","Nil");
        testPatientList.add(patient1);
        Patient patient2 = new Patient("Helen Teo","S2234567I","3B","Nil");
        testPatientList.add(patient2);
        Patient patient3 = new Patient("Anni","S2534569I","6C","Nil");
        testPatientList.add(patient3);
        PatientManager patientManager = new PatientManager(new ArrayList<Patient>());

        patientManager.addPatient(patient1);
        patientManager.addPatient(patient2);
        patientManager.addPatient(patient3);
        assertEquals(patientManager.getPatientList().size(), 3);

        Patient patient4 = new Patient("Anni*","S2534569I","6C","Nil");
        try {
            patientManager.addPatient(patient4);
        } catch (Exception e) {
            String expectedMessage = "Oops! The patient's name cannot contain any special characters.";
            assertEquals(expectedMessage, e.getMessage());
        }

        Patient patient5 = new Patient("Anni","S2534569I","","Nil");
        try {
            patientManager.addPatient(patient5);
        } catch (Exception e) {
            String expectedMessage = "Oops! The patient's Room No. cannot be empty.";
            assertEquals(expectedMessage, e.getMessage());
        }

        Patient patient6 = new Patient("Anni","","32C","Nil");
        try {
            patientManager.addPatient(patient6);
        } catch (Exception e) {
            String expectedMessage = "Oops! NRIC must contain exactly 9 characters.";
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void getPatientListTest() {
        ArrayList<Patient> testPatientList = new ArrayList<>();
        Patient patient1 = new Patient(1,"Lisa Chew","S1234567T","12A","Nil");
        testPatientList.add(patient1);
        Patient patient2 = new Patient(2,"Helen Teo","S2234567I","3B","Nil");
        testPatientList.add(patient2);
        PatientManager patientManager = new PatientManager(testPatientList);

        assertEquals(patientManager.getPatientList(),testPatientList);
    }

    @Test
    public void deletePatientTest() throws DukeException {
        ArrayList<Patient> testPatientList = new ArrayList<>();
        Patient patient1 = new Patient(1,"Lisa Chew","S1234567T","12A","Nil");
        testPatientList.add(patient1);
        Patient patient2 = new Patient(2,"Helen Teo","S2234567I","3B","Nil");
        testPatientList.add(patient2);
        PatientManager patientManager = new PatientManager(testPatientList);

        patientManager.deletePatient(1);
        assertEquals(patientManager.getPatientList().size(), 1);

        try {
            patientManager.deletePatient(3);
        } catch (Exception e) {
            String expectedMessage = "Oops! The patient with id 3 does not exist.";
            assertEquals(expectedMessage, e.getMessage());
        }
    }
}
