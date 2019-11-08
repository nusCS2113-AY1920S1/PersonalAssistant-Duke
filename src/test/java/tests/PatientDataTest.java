package tests;

import duke.data.Patient;
import duke.data.PatientData;
import duke.exception.DukeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class PatientDataTest {

    private Patient patient;
    private PatientData patientData;
    private static final String duplicateBed = "bedNo";

    /**
     * Sets up the core of this object to have a patient and impression, opens the impression, and stores those
     * references for comparison.
     */
    @BeforeEach
    public void setupPatient() {
        try {
            patient = new Patient("name", duplicateBed, "allergies", 0, 0,
                    0, 0, "", "");
            patientData = new PatientData();
            patientData.addPatient(patient);
        } catch (DukeException excp) {
            fail("Exception thrown while setting up basic patient! " + excp.getMessage());
        }
    }

    @Test
    public void duplicateBedSpacePatient_exceptionThrown() {
        Patient duplicatePatient = new Patient("john", duplicateBed, "a", 0, 0,
                0, 0, "", "");
        System.out.println(assertThrows(DukeException.class, () -> {
            patientData.addPatient(duplicatePatient);
        }).getMessage());
    }

    @Test
    public void deletePatient_exceptionThrown() {
        System.out.println(assertThrows(DukeException.class, () -> {
            patientData.deletePatient("unknownStringIdentifierblubcute");
        }).getMessage());
    }

    @Test
    public void deletePatient() {
        try {
            patientData.deletePatient(duplicateBed);
            assertTrue(patientData.getPatientList().isEmpty());
        } catch (DukeException excp) {
            fail("Exception thrown while deleting patient: " + excp.getMessage());
        }
    }
}
