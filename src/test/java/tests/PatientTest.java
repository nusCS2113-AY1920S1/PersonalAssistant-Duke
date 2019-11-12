package tests;

import duke.data.Impression;
import duke.data.Patient;
import duke.exception.DukeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@@author gowgos5
public class PatientTest {
    private final String validBasicPatientStringRepresentation = "Name: John Doe\n"
            + "Personal details\n"
            + "Height: 170\n"
            + "Weight: 50\n"
            + "Age: 30\n"
            + "Number: 98765432\n"
            + "Address: NUS\n"
            + "History: Heart attack 5 years ago\n"
            + "Registration details\n"
            + "Bed Number: A105\n"
            + "Allergies: Cinnarizine\n";
    private final String validBasicPatientWithPrimaryDiagnosisStringRepresentation =
            validBasicPatientStringRepresentation + "\n"
                    + "Primary Diagnosis: \n"
                    + "Impression details\n"
                    + "Name: Hip Fracture\n"
                    + "Description: Patient in pain\n"
                    + "\n"
                    + "Impression details\n"
                    + "Name: Hip Fracture\n"
                    + "Description: Patient in pain\n\n";

    private Patient validPatient;

    /**
     * Sets up a basic Patient object with valid details.
     * This method is called before each JUnit tests in this class.
     */
    @BeforeEach
    public void setupBasicPatient_validDetails() {
        validPatient = new Patient("John Doe", "A105", "Cinnarizine", 170, 50,
                30, 98765432, "NUS", "Heart attack 5 years ago");
    }

    /**
     * Tests getters of a basic Patient object.
     */
    @Test
    public void testEquals_basicPatient_success() {
        assertEquals("John Doe", validPatient.getName());
        assertEquals(170, validPatient.getHeight());
        assertEquals(50, validPatient.getWeight());
        assertEquals(30, validPatient.getAge());
        assertEquals(98765432, validPatient.getNumber());
        assertEquals("Heart attack 5 years ago", validPatient.getHistory());
        assertEquals("A105", validPatient.getBedNo());
        assertEquals("Cinnarizine", validPatient.getAllergies());
        assertTrue(validPatient.equals(validPatient));
    }

    /**
     * Tests toString method of a basic Patient object.
     */
    @Test
    public void testStringRepresentation_basicPatient_matches() {
        assertEquals(validBasicPatientStringRepresentation, validPatient.toString());
    }

    /**
     * Tests toString method of a Patient object with a primary diagnosis.
     */
    @Test
    public void testStringRepresentation_patientWithPrimaryDiagnosis_matches() throws DukeException {
        Impression impression = new Impression("Hip Fracture", "Patient in pain", validPatient);
        validPatient.addNewImpression(impression);
        assertEquals(validBasicPatientWithPrimaryDiagnosisStringRepresentation, validPatient.toString());
    }

    /**
     * Tests add new impression for a Patient object with no impression. The impression should become the primary
     * impression (diagnosis) of the Patient object.
     */
    @Test
    public void addNewImpression_patientWithZeroImpressions_impressionBecomesPrimaryDiagnosis() throws DukeException {
        assertEquals(0, validPatient.getImpressionList().size());

        Impression impression = new Impression("Hip Fracture", "Patient in pain", validPatient);
        validPatient.addNewImpression(impression);
        assertEquals(impression, validPatient.getPrimaryDiagnosis());
    }

    /**
     * Tests add three new impression for a Patient object with no impression. The patient should have three impressions
     * tagged to it, with the first impression added being the primary diagnosis.
     */
    @Test
    public void addThreeImpressions_patientWithZeroImpressions_patientHasThreeImpressions() throws DukeException {
        assertEquals(0, validPatient.getImpressionList().size());

        Impression impressionOne = new Impression("Impression 1", "Description 1", validPatient);
        Impression impressionTwo = new Impression("Impression 2", "Description 2", validPatient);
        Impression impressionThree = new Impression("Impression 3", "Description 3", validPatient);

        validPatient.addNewImpression(impressionTwo);
        validPatient.addNewImpression(impressionOne);
        validPatient.addNewImpression(impressionThree);

        assertEquals(impressionTwo, validPatient.getPrimaryDiagnosis());
        assertEquals(3, validPatient.getImpressionList().size());
    }

    /**
     * Tests add duplicate impression to a Patient with the exact same impression. The test should fail with an
     * exception thrown since there should not be duplicate impressions for any given Patient object.
     */
    @Test
    public void addDuplicateImpression_patientHasThatImpression_exceptionThrown() throws DukeException {
        Impression impressionOne = new Impression("Impression 1", "Description 1", validPatient);

        validPatient.addNewImpression(impressionOne);
        assertEquals(1, validPatient.getImpressionList().size());

        assertThrows(DukeException.class, () -> {
            validPatient.addNewImpression(impressionOne);
        });
    }

    /**
     * Tests delete an impression for a Patient object with two impressions. The Patient object should have one
     * impression remaining, and that impression will be the primary diagnosis of the Patient object.
     */
    @Test
    public void deleteOneImpressions_patientWithTwoImpressions_patientHasOneImpressionThatIsPrimaryDiagnosis()
            throws DukeException {
        Impression impressionOne = new Impression("Impression 1", "Description 1", validPatient);
        Impression impressionTwo = new Impression("Impression 2", "Description 2", validPatient);

        validPatient.addNewImpression(impressionOne);
        validPatient.addNewImpression(impressionTwo);

        assertEquals(impressionOne, validPatient.getPrimaryDiagnosis());
        assertEquals(2, validPatient.getImpressionList().size());

        validPatient.deleteImpression(impressionOne.getName());

        assertEquals(impressionTwo, validPatient.getPrimaryDiagnosis());
        assertEquals(1, validPatient.getImpressionList().size());
    }

    /**
     * Tests delete an impression for a Patient object with zero impressions. The test should fail with an exception
     * thrown as there are no impressions to be deleted for the Patient object.
     */
    @Test
    public void deleteOneImpressions_patientWithZeroImpressions_exceptionThrown() throws DukeException {
        Impression impressionOne = new Impression("Impression 1", "Description 1", validPatient);

        validPatient.addNewImpression(impressionOne);
        validPatient.deleteImpression(impressionOne.getName());

        assertThrows(DukeException.class, () -> {
            validPatient.deleteImpression(impressionOne.getName());
        });
    }

    /**
     * Tests get impressions for a Patient object.
     */
    @Test
    public void getImpressions_patientWithTwoImpressions_impressionsMatch() throws DukeException {
        Impression impressionOne = new Impression("Impression 1", "Description 1", validPatient);
        Impression impressionTwo = new Impression("Impression 2", "Description 2", validPatient);

        validPatient.addNewImpression(impressionOne);
        validPatient.addNewImpression(impressionTwo);

        assertEquals(impressionOne, validPatient.getImpression(impressionOne.getName()));
        assertEquals(impressionTwo, validPatient.getImpression(impressionTwo.getName()));
    }

    /**
     * Tests append history for a Patient object. The history of the Patient object should be successfully appended
     * with the newly added notes..
     */
    @Test
    public void appendHistory_basicPatient_historySuccessfullyAppended() {
        assertEquals("Heart attack 5 years ago", validPatient.getHistory());
        validPatient.appendHistory("Test Append");
        assertEquals("Heart attack 5 years ago\n\nTest Append",
                validPatient.getHistory());
    }
}
